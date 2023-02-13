package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.service.ApplicantService;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.JobDto.JobCreateDto;
import ar.edu.itba.paw.webapp.dto.JobDto.JobDto;
import ar.edu.itba.paw.webapp.helpers.UriHelper;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Path("/jobs")
@Component
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private UriHelper uriHelper;

    @Context
    UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);
    private static final int PAGE_SIZE = 9;


    @GET
    @Path("")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response filterJobs(
            @QueryParam("name") String name,
            @QueryParam("experience") @Min(0) @Max(100) Long experienceYears,
            @QueryParam("location") @Pattern(regexp = "[1-4][,[1-4]]*") @Size(max = 7) String location,
            @QueryParam("availability") @Pattern(regexp = "[1-3][,[1-3]]*") @Size(max = 5) String availability,
            @QueryParam("abilities") @Pattern(regexp = "[1-6][,[1-6]]*") @Size(max = 11) String abilities,
            @QueryParam("page") @DefaultValue("0") Long page,
            @Context HttpServletRequest request
    ) {

        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);

        List<JobDto> jobs = jobService.getFilteredJobs(name, experienceYears, location, availability, abilities, page, PAGE_SIZE).stream().map(j -> {
            int status = applicantService.getStatus(principal.getUserID(), j.getJobId());
            return JobDto.fromJob(uriInfo, j, status);
        }).collect(Collectors.toList());

        int pages = jobService.getPageNumber(name, experienceYears, location, availability, abilities, PAGE_SIZE);
        GenericEntity<List<JobDto>> genericEntity = new GenericEntity<List<JobDto>>(jobs) { };
        Response.ResponseBuilder responseBuilder = Response.ok(genericEntity);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        if (request.getQueryString() != null) {
            UriHelper.fillQueryParams(uriBuilder, name, experienceYears, location, availability, abilities, "rating");
        }
        return uriHelper.addPaginationLinksForExplore(responseBuilder, uriBuilder, page, pages)
                .header("Access-Control-Expose-Headers", "Total-Count")
                .header("Total-Count", pages)
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response verTrabajo(@PathParam("id") long id, @Context HttpServletRequest request) throws JobNotFoundException {
        Job job;
        try {
            job = jobService.getJobByID(id);
        } catch (JobNotFoundException exception) {
            LOGGER.error("an exception occurred:", exception);
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            LOGGER.error("an exception occurred:", ex);
            return Response.status(Response.Status.CONFLICT).build();
        }
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);
        int status = applicantService.getStatus(principal.getUserID(), job.getJobId());

        JobDto jobDto = JobDto.fromJob(uriInfo, job, status);

        GenericEntity<JobDto> genericEntity = new GenericEntity<JobDto>(jobDto) { };
        return Response.ok(genericEntity).build();
    }

    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response postJob(@Valid JobCreateDto jobCreateDto) {

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Job job = jobService.create(jobCreateDto.getTitle(), jobCreateDto.getLocation(), hogarUser.getUserID(), jobCreateDto.getAvailability(), jobCreateDto.getExperienceYears(), fromArrayToString(jobCreateDto.getAbilities()), jobCreateDto.getDescription());
        if (job == null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        LOGGER.debug(String.format("job created under jobid %d", job.getJobId()));
        return Response.created(uriInfo.getBaseUriBuilder().path("/jobs").path(String.valueOf(job.getJobId())).build()).header("Access-Control-Expose-Headers", "Location").build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response deleteJob(@PathParam("id") long id) {
        try {
            jobService.getJobByID(id);
        } catch (JobNotFoundException exception) {
            LOGGER.error("an exception occurred:", exception);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
//
//        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (hogarUser.getUserID() != job.getEmployerId().getId().getId()) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }

        jobService.deleteJob(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response updateJobStatus(@PathParam("id") long id,
                                    @NotNull @FormDataParam("status") Boolean status) throws JobNotFoundException {
        
        try {
            jobService.getJobByID(id);
        } catch (JobNotFoundException exception) {
            LOGGER.error("an exception occurred:", exception);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
//        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (hogarUser.getUserID() != job.getEmployerId().getId().getId()) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }
        if (status) {
            jobService.openJob(id);
        } else {
            jobService.closeJob(id);
        }
        return Response.ok(status).build();
    }

    private String fromArrayToString(String[] arr) {
        StringBuilder ret = new StringBuilder();
        for (String str : arr) {
            ret.append(str).append(",");
        }
        return ret.substring(0, ret.length() - 1);
    }

}
