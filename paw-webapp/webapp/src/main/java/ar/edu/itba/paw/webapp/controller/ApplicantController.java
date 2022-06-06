package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ApplicantController {

    //TODO: poner mas
    private final int PAGE_SIZE = 4;
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

    @RequestMapping(value = "/apply/{jobID}", method = {RequestMethod.POST})
    public ModelAndView apply(@PathVariable final long jobID){
        ModelAndView mav = new ModelAndView("redirect:/trabajo/"+jobID);
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optional = userService.getUserById(principal.getUserID());
        try {
            optional.ifPresent(user -> applicantService.apply(jobID, user));
            mav.addObject("status", "sent");
        } catch (AlreadyExistsException alreadyExistsException) {
            LOGGER.error(String.format("there has already been made a contact for %d by %s", jobID, principal.getName()));
            mav.addObject("status", "error");
        }
        return mav;
    }

    @RequestMapping(value = "/aplicantes/{jobID}", method = {RequestMethod.GET})
    public ModelAndView applicants(@PathVariable final int jobID,
                            @RequestParam(value = "page", required = false) Long page){
        ModelAndView mav = new ModelAndView("viewApplicants");
        if (page == null)
            page = 0L;
        List<Applicant> list = applicantService.getApplicantsByJob(jobID,page,PAGE_SIZE);
        for(Applicant applicant : list){
            applicant.firstWordsToUpper(applicant.getEmployeeID());
        }
        mav.addObject("ApplicantList", list);
        mav.addObject("title", jobService.getJobNameById(jobID));
        mav.addObject("page", page);
        mav.addObject("maxPage",applicantService.getPageNumber(jobID, PAGE_SIZE));
        return mav;
    }

    @RequestMapping(value = "/changeStatus/{jobId}/{employeeId}/{status}", method = {RequestMethod.POST})
    public ModelAndView changeStatus(@PathVariable final int jobId, @PathVariable final int employeeId, @PathVariable final int status){
        applicantService.changeStatus(status, employeeId, jobId);
        Optional<Job> job = jobService.getJobByID(jobId);
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        if(job.isPresent() && employee.isPresent()){
            contactService.changedStatus(status, job.get(), employee.get());
        }
        return new ModelAndView("redirect:/aplicantes/" + jobId);
    }

    @RequestMapping(value="/trabajosAplicados", method = {RequestMethod.GET})
    public ModelAndView appliedTo(@RequestParam(value = "page", required = false) Long page){
        ModelAndView mav = new ModelAndView("appliedJobs");
        if (page == null)
            page = 0L;
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Job> list = applicantService.getJobsByApplicant(principal.getUserID(), page, PAGE_SIZE);
        Map<Job, Integer> jobList = new HashMap<>();
        mav.addObject("page", page);
        mav.addObject("maxPage",applicantService.getPageNumberForAppliedJobs(principal.getUserID(), PAGE_SIZE));
        for (Job job : list) {
            job.firstWordsToUpper();
            job.getEmployerId().firstWordsToUpper(job.getEmployerId());
            int status = applicantService.getStatus(principal.getUserID(), job.getJobId());
            jobList.put(job, status);
        }
        mav.addObject("jobList", jobList);
        return mav;
    }
}
