package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.ApplicantService;
import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.ApplicantDto.ApplicantCreateDto;
import ar.edu.itba.paw.webapp.dto.ApplicantDto.ApplicantEditDto;
import ar.edu.itba.paw.webapp.dto.ApplicantDto.ApplicantInJobsDto;
import ar.edu.itba.paw.webapp.helpers.UriHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/applicants")
@Component
public class ApplicantController {
    @Autowired
    private JobService jobService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicantController.class);
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private UriHelper uriHelper;

    @Context
    private UriInfo uriInfo;

    private static final int PAGE_SIZE = 5;

    @GET
    @Path("/{employeeId}/{jobId}")
    public Response getStatusApplication(@PathParam("employeeId") long employeeId,
                                         @PathParam("jobId") long jobId) {
        int status = applicantService.getStatus(employeeId, jobId);
        return Response.ok(status).build();
    }

    @GET
    @Path("/{jobId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response applicants(@PathParam("jobId") long jobId, @QueryParam("page") @DefaultValue("0") Long page) {

        try {
            jobService.getJobByID(jobId);
        } catch (JobNotFoundException exception) {
            LOGGER.error("an exception occurred:", exception);
            return Response.status(Response.Status.NOT_FOUND).entity("404 - Not Found").build();
        } catch (Exception exception) {
            LOGGER.error("an exception occurred:", exception);
            return Response.status(Response.Status.CONFLICT).build();
        }


        List<ApplicantInJobsDto> list = applicantService.getApplicantsByJob(jobId, page, PAGE_SIZE)
                .stream()
                .map(a -> ApplicantInJobsDto.fromJob(uriInfo, a))
                .collect(Collectors.toList());
        int pages = applicantService.getPageNumber(jobId, PAGE_SIZE);
        GenericEntity<List<ApplicantInJobsDto>> genericEntity = new GenericEntity<List<ApplicantInJobsDto>>(list) { };
        Response.ResponseBuilder responseBuilder = Response.ok(genericEntity);
        uriHelper.addPaginationLinks(responseBuilder, uriInfo, page, pages);
        return responseBuilder
                .header("Total-Count", pages)
                .build();
    }

    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response createApplicant(@Valid ApplicantCreateDto applicantCreateDto, @Context HttpServletRequest request) throws UserNotFoundException {

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0,2));
        LocaleContextHolder.setLocale(locale);

        try {
            applicantService.apply(applicantCreateDto.getJobId(), hogarUser.getUserID());
        } catch (AlreadyExistsException alreadyExistsException) {
            LOGGER.error(String.format("The user %d has already applied to job %d", hogarUser.getUserID(), applicantCreateDto.getJobId()));
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }


    @PUT
    @Path("/{employeeId}/{jobId}")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response changeStatus(@PathParam("employeeId") long employeeId,
                                 @PathParam("jobId") long jobId,
                                 @Valid ApplicantEditDto applicantEditDto,
                                 @Context HttpServletRequest request) throws JobNotFoundException, UserNotFoundException {

        Job job;
        Employee employee;

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 2));
        LocaleContextHolder.setLocale(locale);

        try {
            job = jobService.getJobByID(jobId);
            employee = employeeService.getEmployeeById(employeeId);

            int finalStatus = applicantService.changeStatus(applicantEditDto.getStatus(), employeeId, jobId);
            contactService.changedStatus(applicantEditDto.getStatus(), job, employee);
            return Response.ok(finalStatus).build();

        } catch (UserNotFoundException | JobNotFoundException ex) {
            LOGGER.error("an exception occurred:", ex);
            return Response.status(Response.Status.NOT_FOUND).entity("404 - Not Found").build();
        } catch (Exception exception) {
            LOGGER.error("an exception occurred:", exception);
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("/{employeeId}/{jobId}")
    public Response deleteApplication(@PathParam("employeeId") long employeeId,
                                      @PathParam("jobId") long jobId) {

        applicantService.withdrawApplication(employeeId, jobId);
        return Response.noContent().build();
    }

}
