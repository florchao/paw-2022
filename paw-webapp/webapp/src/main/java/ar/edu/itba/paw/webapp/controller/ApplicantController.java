package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Applicant;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class ApplicantController {

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
    ModelAndView applicants(@PathVariable final int jobID){
        ModelAndView mav = new ModelAndView("viewApplicants");
        Optional<List<Applicant>> list = applicantService.getApplicantsByJob(jobID);
        list.ifPresent(applicants -> mav.addObject("ApplicantList", applicants));
        mav.addObject("title", jobService.getJobNameById(jobID));
        return mav;
    }

}
