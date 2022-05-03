package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.ApplicantService;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ApplicantController {

    @Autowired
    JobService jobService;

    @Autowired
    UserService userService;

    @Autowired
    ApplicantService applicantService;

    @RequestMapping(value = "/apply/{jobID}", method = {RequestMethod.POST})
    ModelAndView apply(@PathVariable final long jobID){
        ModelAndView mav = new ModelAndView("redirect:/trabajo/"+jobID);
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optional = userService.findByUsername(principal.getUsername());
        optional.ifPresent(user -> applicantService.create(jobID, user.getId()));
        return mav;
    }

}
