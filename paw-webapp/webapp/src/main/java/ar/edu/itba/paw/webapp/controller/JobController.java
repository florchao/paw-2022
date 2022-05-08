package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.JobForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class JobController {
    @Autowired
    JobService jobService;

    @Autowired
    UserService userService;

    @RequestMapping("/crearTrabajo")
    ModelAndView crearTrabajo(@ModelAttribute("jobForm")final JobForm form){
        return new ModelAndView("createJob");
    }

    @RequestMapping("/createJob")
    ModelAndView create(@Valid @ModelAttribute("jobForm") final JobForm form, final BindingResult errors){
        if(errors.hasErrors())
            return crearTrabajo(form);
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jobService.create(form.getTitle(), form.getLocation(), principal.getUserID(), form.getAvailability(), form.getExperienceYears(), form.getAbilities(), form.getDescription());
        return new ModelAndView("redirect:/buscarEmpleadas");
    }

    @RequestMapping("/trabajos")
    ModelAndView verTrabajos(){
        ModelAndView mav = new ModelAndView("publishedJobs");
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<List<Job>> jobs = jobService.getUserJobs(principal.getUserID());
        mav.addObject("JobList", jobs.get());
        return mav;
    }

    @RequestMapping("/trabajo/{id}")
    ModelAndView verTrabajo(@PathVariable final long id, @RequestParam(value = "status", required = false) String status){
        ModelAndView mav = new ModelAndView("viewJob");
        Optional<Job> job = jobService.getJobByID(id);
        job.ifPresent(value -> mav.addObject("job", value));
        mav.addObject("status", status);
        return mav;
    }
}
