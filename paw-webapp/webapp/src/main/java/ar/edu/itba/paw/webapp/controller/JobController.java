package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.service.ApplicantService;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.ApplicantDto.ApplicantInJobsDto;
import ar.edu.itba.paw.webapp.dto.JobDto.JobCreateDto;
import ar.edu.itba.paw.webapp.dto.JobDto.JobDto;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/api/jobs")
@Component
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicantService applicantService;

    @Context
    UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);
    private static final int PAGE_SIZE = 9;


    @GET
    @Path("")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response filterJobs(
            @QueryParam("name") String name,
            @QueryParam("experience") Long experienceYears,
            @QueryParam("location") String location,
            @QueryParam("availability") String availability,
            @QueryParam("abilities") String abilities,
            @QueryParam("page") @DefaultValue("0") Long page,
            @Context HttpServletRequest request
    ) {
        if (experienceYears != null && (experienceYears < 0 || experienceYears > 100)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (location != null && (!location.matches("[1-4][,[1-4]]*") || location.length() > 7)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (availability != null && (!availability.matches("[1-3][,[1-3]]*") || availability.length() > 5))
            return Response.status(Response.Status.BAD_REQUEST).build();
        if (abilities != null && (!abilities.matches("[1-6][,[1-6]]*") || abilities.length() > 11))
            return Response.status(Response.Status.BAD_REQUEST).build();

        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);

        List<JobDto> jobs = jobService.getFilteredJobs(name, experienceYears, location, availability, abilities, page, PAGE_SIZE).stream().map(j -> {
            int status = applicantService.getStatus(principal.getUserID(), j.getJobId());
            return JobDto.fromExplore(uriInfo, j, status);
        }).collect(Collectors.toList());

        int pages = jobService.getPageNumber(name, experienceYears, location, availability, abilities, PAGE_SIZE);
        GenericEntity<List<JobDto>> genericEntity = new GenericEntity<List<JobDto>>(jobs) {
        };
        return Response.ok(genericEntity).header("Access-Control-Expose-Headers", "X-Total-Count").header("X-Total-Count", pages).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response verTrabajo(@PathParam("id") long id, @Context HttpServletRequest request) throws JobNotFoundException {
        Job job;
        try {
            job = jobService.getJobByID(id);
        } catch (JobNotFoundException exception) {
            exception.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.CONFLICT).build();
        }

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);

        JobDto jobDto = JobDto.fromJob(uriInfo, job);

        GenericEntity<JobDto> genericEntity = new GenericEntity<JobDto>(jobDto) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/{jobId}/applicants")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response applicants(@PathParam("jobId") long jobId, @QueryParam("page") @DefaultValue("0") Long page) {

        Job job;
        try {
            job = jobService.getJobByID(jobId);
        } catch (JobNotFoundException exception) {
            exception.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception exception) {
            exception.printStackTrace();
            return Response.status(Response.Status.CONFLICT).build();
        }

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (hogarUser.getUserID() != job.getEmployerId().getId().getId()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<ApplicantInJobsDto> list = applicantService.getApplicantsByJob(jobId, page, PAGE_SIZE)
                .stream()
                .map(a -> ApplicantInJobsDto.fromJob(uriInfo, a))
                .collect(Collectors.toList());
        int pages = applicantService.getPageNumber(jobId, PAGE_SIZE);
        GenericEntity<List<ApplicantInJobsDto>> genericEntity = new GenericEntity<List<ApplicantInJobsDto>>(list) {
        };
        return Response.ok(genericEntity).header("Access-Control-Expose-Headers", "X-Total-Count").header("X-Total-Count", pages).build();
    }


    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response postJob(@Valid JobCreateDto jobCreateDto) {
//        if (title.isEmpty() || (title.length() > 25) ||
//                (location.length() > 1) || !location.matches("[1-4]") ||
//                Objects.isNull(experienceYears) || (experienceYears < 0) || (experienceYears > 100) ||
//                availability.isEmpty() || !availability.matches("[1-3]") || abilities.isEmpty()
//                || (abilities.size() > 6))
//            return Response.status(Response.Status.BAD_REQUEST).build();

//        for (int i = 0; i < abilities.toArray().length; i++) {
//            if (!abilities.get(i).matches("[1-6]"))
//                return Response.status(Response.Status.BAD_REQUEST).build();
//        }

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Job job = jobService.create(jobCreateDto.getTitle(), jobCreateDto.getLocation(), hogarUser.getUserID(), jobCreateDto.getAvailability(), jobCreateDto.getExperienceYears(), fromArrayToString(jobCreateDto.getAbilities()), jobCreateDto.getDescription());
        if (job == null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        LOGGER.debug(String.format("job created under jobid %d", job.getJobId()));
        return Response.status(Response.Status.CREATED).entity(uriInfo.getBaseUriBuilder().path("/api/jobs").path(String.valueOf(job.getJobId())).build()).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response deleteJob(@PathParam("id") long id) {

        Job job;
        try {
            job = jobService.getJobByID(id);
        } catch (JobNotFoundException exception) {
            exception.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (hogarUser.getUserID() != job.getEmployerId().getId().getId()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        jobService.deleteJob(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response updateJobStatus(@PathParam("id") long id,
                                    @NotNull @FormDataParam("status") Boolean status) throws JobNotFoundException {

        Job job;
        try {
            job = jobService.getJobByID(id);
        } catch (JobNotFoundException exception) {
            exception.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (hogarUser.getUserID() != job.getEmployerId().getId().getId()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (status) {
            jobService.openJob(id);
        } else {
            jobService.closeJob(id);
        }
        return Response.ok(status).build();
    }

    private String fromListToString(List<String> arr) {
        StringBuilder ret = new StringBuilder();
        for (String str : arr) {
            ret.append(str).append(",");
        }
        return ret.substring(0, ret.length() - 1);
    }

    private String[] fromListToArray(List<String> arr) {
        String[] str = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            str[i] = arr.get(i);
        }
        return str;
    }

    private String fromArrayToString(String[] arr) {
        StringBuilder ret = new StringBuilder();
        for (String str : arr) {
            ret.append(str).append(",");
        }
        return ret.substring(0, ret.length() - 1);
    }

}
