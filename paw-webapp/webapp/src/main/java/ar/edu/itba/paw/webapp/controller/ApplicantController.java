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
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Locale;
import java.util.Objects;

@Path("/api/applicants")
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

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{employeeId}/{jobId}")
    public Response getStatusApplication(@PathParam("employeeId") long employeeId,
                                         @PathParam("jobId") long jobId) {
        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (hogarUser.getUserID() != employeeId) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        int status = applicantService.getStatus(employeeId, jobId);
        return Response.ok(status).build();
    }

    @POST
    @Path("")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response createApplicant(@FormDataParam("jobId") Long jobId, @Context HttpServletRequest request) throws UserNotFoundException {

        if (Objects.isNull(jobId))
            return Response.status(Response.Status.BAD_REQUEST).build();

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0,2));
        LocaleContextHolder.setLocale(locale);

        try {
            applicantService.apply(jobId, hogarUser.getUserID());
        } catch (AlreadyExistsException alreadyExistsException) {
            LOGGER.error(String.format("there has already been made a contact for %d by id %d", jobId, hogarUser.getUserID()));
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }


    @PUT
    @Path("/{employeeId}/{jobId}")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response changeStatus(@PathParam("employeeId") long employeeId,
                                 @PathParam("jobId") long jobId,
                                 @FormDataParam("status") Integer status,
                                 @Context HttpServletRequest request) throws JobNotFoundException, UserNotFoundException {

        if (Objects.isNull(status)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Job job;
        Employee employee;

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 2));
        LocaleContextHolder.setLocale(locale);

        try {
            job = jobService.getJobByID(jobId);
            employee = employeeService.getEmployeeById(employeeId);
            HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (hogarUser.getUserID() != job.getEmployerId().getId().getId()) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }

            int finalStatus = applicantService.changeStatus(status, employeeId, jobId);
            contactService.changedStatus(status, job, employee);
            return Response.ok(finalStatus).build();

        } catch (UserNotFoundException | JobNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception exception) {
            exception.printStackTrace();
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("/{employeeId}/{jobId}")
    public Response deleteApplication(@PathParam("employeeId") long employeeId,
                                      @PathParam("jobId") long jobId) {

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (hogarUser.getUserID() != employeeId) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        applicantService.withdrawApplication(employeeId, jobId);
        return Response.noContent().build();
    }

}
