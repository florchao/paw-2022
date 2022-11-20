package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.ApplicantDto;
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
@Path("/api/applicant")
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

//    @RequestMapping(value = "/apply/{jobID}", method = {RequestMethod.POST})
//    public ModelAndView apply(@PathVariable final long jobID) throws UserNotFoundException {
//        ModelAndView mav = new ModelAndView("redirect:/trabajo/"+jobID);
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User optional = userService.getUserById(principal.getUserID());
//        try {
//            applicantService.apply(jobID, optional);
//            mav.addObject("status", "sent");
//        } catch (AlreadyExistsException alreadyExistsException) {
//            LOGGER.error(String.format("there has already been made a contact for %d by %s", jobID, principal.getName()));
//            mav.addObject("status", "error");
//        }
//        return mav;
//    }
//
//    @RequestMapping(value = "/aplicantes/{jobID}", method = {RequestMethod.GET})
//    public ModelAndView applicants(@PathVariable final int jobID, @RequestParam(value = "page", required = false) Long page) throws JobNotFoundException{
//        ModelAndView mav = new ModelAndView("viewApplicants");
//        if (page == null)
//            page = 0L;
//        List<Applicant> list = applicantService.getApplicantsByJob(jobID,page,PAGE_SIZE);
//        for(Applicant applicant : list){
//            applicant.firstWordsToUpper(applicant.getEmployeeID());
//        }
//        Job job = jobService.getJobByID(jobID).orElseThrow(()-> new JobNotFoundException("job" + jobID + "does not exists"));
//        mav.addObject("ApplicantList", list);
//        mav.addObject("title", job.getTitle());
//        mav.addObject("page", page);
//        mav.addObject("maxPage",applicantService.getPageNumber(jobID, PAGE_SIZE));
//        return mav;
//    }

    @GET
    @Path("/{jobId}")
    @Produces(value = { MediaType.APPLICATION_JSON })
    public Response applicants(@PathParam("jobId") long jobId, @QueryParam("page") Long page){
        List<ApplicantDto> list = applicantService.getApplicantsByJob(jobId,0L,PAGE_SIZE).stream().map(a -> ApplicantDto.fromJob(uriInfo, a)).collect(Collectors.toList());
        GenericEntity<List<ApplicantDto>> genericEntity = new GenericEntity<List<ApplicantDto>>(list){};
        return Response.ok(genericEntity).build();
    }

    //TODO: que sea solo jobs cuando tengamos el token
    @GET
    @Path("{id}/jobs")
    @Produces(value = { MediaType.APPLICATION_JSON })
    public Response appliedTo(@QueryParam("page") Long page, @PathParam("id") long id){
//        ModelAndView mav = new ModelAndView("appliedJobs");
//        if (page == null)
//            page = 0L;
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ApplicantDto> list = applicantService.getAppliedJobsByApplicant(id, 0L, PAGE_SIZE).stream().map(applicant -> ApplicantDto.fromEmployee(uriInfo, applicant)).collect(Collectors.toList());
//        Map<Job, Integer> jobList = new LinkedHashMap<>();
//        mav.addObject("page", page);
//        mav.addObject("maxPage",applicantService.getPageNumberForAppliedJobs(principal.getUserID(), PAGE_SIZE));
//        for (Job job : list) {
//            job.firstWordsToUpper();
//            job.locationNameToUpper();
//            job.getEmployerId().firstWordsToUpper();
//            int status = applicantService.getStatus(principal.getUserID(), job.getJobId());
//            jobList.put(job, status);
//        }
//        mav.addObject("jobList", jobList);
//        return mav;
        GenericEntity<List<ApplicantDto>> genericEntity = new GenericEntity<List<ApplicantDto>>(list){};
        return Response.ok(genericEntity).build();
    }
//
//    @RequestMapping(value = "/changeStatus/{jobId}/{employeeId}/{status}", method = {RequestMethod.POST})
//    public ModelAndView changeStatus(@PathVariable final int jobId, @PathVariable final int employeeId, @PathVariable final int status) throws JobNotFoundException, UserNotFoundException {
//        applicantService.changeStatus(status, employeeId, jobId);
//        Job job = jobService.getJobByID(jobId).orElseThrow(()-> new JobNotFoundException("job" + jobId + "does not exists"));
//        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
//        employee.ifPresent(value -> contactService.changedStatus(status, job, value));
//        return new ModelAndView("redirect:/aplicantes/" + jobId);
//    }
//
//    @RequestMapping(value="/trabajosAplicados", method = {RequestMethod.GET})
//    public ModelAndView appliedTo(@RequestParam(value = "page", required = false) Long page){
//        ModelAndView mav = new ModelAndView("appliedJobs");
//        if (page == null)
//            page = 0L;
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        List<Job> list = applicantService.getJobsByApplicant(principal.getUserID(), page, PAGE_SIZE);
//        Map<Job, Integer> jobList = new LinkedHashMap<>();
//        mav.addObject("page", page);
//        mav.addObject("maxPage",applicantService.getPageNumberForAppliedJobs(principal.getUserID(), PAGE_SIZE));
//        for (Job job : list) {
//            job.firstWordsToUpper();
//            job.locationNameToUpper();
//            job.getEmployerId().firstWordsToUpper();
//            int status = applicantService.getStatus(principal.getUserID(), job.getJobId());
//            jobList.put(job, status);
//        }
//        mav.addObject("jobList", jobList);
//        return mav;
//    }
}
