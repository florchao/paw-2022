package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.ApplicantDto;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jboss.logging.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.stream.Collectors;
@Path("/api/applicants")
@Component
public class ApplicantController {

    //TODO: poner mas
    private final int PAGE_SIZE = 5;
    @Autowired
    private JobService jobService;
    @Autowired
    private UserService userService;
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
                                         @PathParam("jobId") long jobId){
        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(hogarUser.getUserID() != employeeId){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        int status = applicantService.getStatus(employeeId, jobId);
        return Response.ok(status).build();
    }
    @POST
    @Path("")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA, })
    public Response createApplicant(@FormDataParam("jobId") Long jobId) throws UserNotFoundException{

        if(Objects.isNull(jobId))
            return Response.status(Response.Status.BAD_REQUEST).build();

        //todo creo que esto hay que chequearle los permisos
        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            applicantService.apply(jobId, hogarUser.getUserID());
        } catch (AlreadyExistsException alreadyExistsException) {
            LOGGER.error(String.format("there has already been made a contact for %d by id %d", jobId, hogarUser.getUserID()));
            return Response.ok(-1).build();
        }
        return Response.status(Response.Status.CREATED).entity(0).build();
    }



    @PUT
    @Path("/{employeeId}/{jobId}")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA, })
    public Response changeStatus(@PathParam("employeeId") long employeeId,
                                 @PathParam("jobId") long jobId,
                                 @FormDataParam("status") Integer status) throws JobNotFoundException, UserNotFoundException{

        if(Objects.isNull(status)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Job job = jobService.getJobByID(jobId);

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(hogarUser.getUserID() != job.getEmployerId().getId().getId()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        int finalStatus = applicantService.changeStatus(status, employeeId, jobId);

        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        employee.ifPresent(value -> contactService.changedStatus(status, job, value));
        return Response.ok(finalStatus).build();
    }

    @DELETE
    @Path("/{employeeId}/{jobId}")
    public Response deleteApplication(@PathParam("employeeId") long employeeId,
                                      @PathParam("jobId") long jobId){

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(hogarUser.getUserID() != employeeId){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        applicantService.withdrawApplication(employeeId,jobId);
        return Response.noContent().build();
    }

}
