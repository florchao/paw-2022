package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.service.ApplicantService;
import ar.edu.itba.paw.service.EmployerService;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.service.UserService;
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

import java.util.List;
import java.util.Optional;

@Controller
public class ApplicantController {

    private final int PAGE_SIZE = 1;

    @Autowired
    JobService jobService;

    @Autowired
    UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicantController.class);

    @Autowired
    EmployerService employerService;

    @Autowired
    ApplicantService applicantService;

    @RequestMapping(value = "/apply/{jobID}", method = {RequestMethod.POST})
    ModelAndView apply(@PathVariable final long jobID){
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
    ModelAndView applicants(@PathVariable final int jobID,
                            @RequestParam(value = "page", required = false) Long page){
        ModelAndView mav = new ModelAndView("viewApplicants");
        if (page == null)
            page = 0L;
        Optional<List<Applicant>> list = applicantService.getApplicantsByJob(jobID,page,PAGE_SIZE);
        list.ifPresent(applicants -> mav.addObject("ApplicantList", applicants));
        mav.addObject("title", jobService.getJobNameById(jobID));
        mav.addObject("page", page);
        mav.addObject("maxPage",applicantService.getPageNumber(jobID, PAGE_SIZE));
        return mav;
    }

    @RequestMapping(value = "/changeStatus/{jobId}/{employeeId}/{status}", method = {RequestMethod.POST})
    ModelAndView changeStatus(@PathVariable final int jobId, @PathVariable final int employeeId, @PathVariable final int status){
        System.out.println("STATUS");
        int aaaaa = applicantService.changeStatus(status, employeeId, jobId);
        System.out.print("AFTER: ");
        System.out.println(aaaaa);
        return new ModelAndView("redirect:/aplicantes/" + jobId);
    }

    @RequestMapping(value="/trabajosAplicados", method = {RequestMethod.GET})
    ModelAndView appliedTo(@RequestParam(value = "page", required = false) Long page){
        ModelAndView mav = new ModelAndView("appliedJobs");
        if (page == null)
            page = 0L;
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<List<Job>> list = applicantService.getJobsByApplicant(principal.getUserID(), page, PAGE_SIZE);
        mav.addObject("page", page);
        mav.addObject("maxPage",applicantService.getPageNumberForAppliedJobs(principal.getUserID(), PAGE_SIZE));


        if (list.isPresent()) {
            for (Job job : list.get()) {
                job.firstWordsToUpper();
            }
        }

        list.ifPresent(jobs -> mav.addObject("JobsList", jobs));

        return mav;
    }
}
