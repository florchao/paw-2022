package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.form.JobForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class JobController {
    @Autowired
    JobService jobService;

    @Autowired
    UserService userService;

    private final static long PAGE_SIZE = 8;

    @RequestMapping("/crearTrabajo")
    ModelAndView crearTrabajo(@ModelAttribute("jobForm")final JobForm form){
        return new ModelAndView("createJob");
    }

    @RequestMapping("/createJob")
    ModelAndView create(@Valid @ModelAttribute("jobForm") final JobForm form, final BindingResult errors){
        if(errors.hasErrors())
            return crearTrabajo(form);
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jobService.create(form.getTitle(), form.getLocation(), principal.getUserID(), form.getAvailability(), form.getExperienceYears(), form.fromArrtoString(form.getAbilities()), form.getDescription());
        return new ModelAndView("redirect:/buscarEmpleadas");
    }

    @RequestMapping("/misTrabajos")
    ModelAndView verTrabajos(){
        ModelAndView mav = new ModelAndView("publishedJobs");
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<List<Job>> jobs = jobService.getUserJobs(principal.getUserID());
        List<Job> jobList = new ArrayList<>();
        if (jobs.isPresent()) {
            for (Job job : jobs.get()) {
                job.firstWordsToUpper();
                jobList.add(job);
            }
        }
        mav.addObject("JobList", jobList);
        return mav;
    }

    @RequestMapping("/trabajo/{id}")
    ModelAndView verTrabajo(@PathVariable final long id, @RequestParam(value = "status", required = false) String status){
        ModelAndView mav = new ModelAndView("viewJob");
        Optional<Job> job = jobService.getJobByID(id);
        if (job.isPresent()) {
            job.get().employerNameToUpper();
            job.get().firstWordsToUpper();
            mav.addObject("job", job.get());
        }
        mav.addObject("status", status);
        return mav;
    }

    @RequestMapping("/trabajos")
    ModelAndView searchJobs(
            @ModelAttribute("filterJobsBy") FilterForm jobForm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "experienceYears", required = false) Long experienceYears,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "availability", required = false) String availability,
            @RequestParam(value = "abilities", required = false) String abilities,
            @RequestParam(value = "page", required = false) Long page) {
        if (page == null)
            page = 0L;
        List<Job> jobList = new ArrayList<>();
        Optional<List<Job>> opJob = jobService.getFilteredJobs(name, experienceYears, location, availability, abilities, page, PAGE_SIZE);
        if (opJob.isPresent()) {
            for (Job job : opJob.get()) {
                job.firstWordsToUpper();
                jobList.add(job);
            }
        }


        final ModelAndView mav = new ModelAndView("searchJobs");
        mav.addObject("jobList", jobList);
        mav.addObject("page", page);
        mav.addObject("maxPage", jobService.getPageNumber(name, experienceYears, location, availability, abilities, PAGE_SIZE));
        return mav;
    }

    @RequestMapping(value = "/filterJobs", method = {RequestMethod.GET})
    public ModelAndView filterEmployees(@Valid @ModelAttribute("filterJobsBy") FilterForm form, final BindingResult errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return searchJobs(null, null,null,null,null,null,null);
        }
        if (!Objects.equals(form.getName(),""))
            redirectAttributes.addAttribute("name",form.getName());
        if (form.getExperienceYears() > 0)
            redirectAttributes.addAttribute("experienceYears", form.getExperienceYears());
        if (!Objects.equals(form.getLocation(), ""))
            redirectAttributes.addAttribute("location", form.getLocation());
        if (form.getAvailability() != null && form.getAvailability().length > 0)
            redirectAttributes.addAttribute("availability", form.getAvailability());
        if (form.getAbilities() != null && form.getAbilities().length > 0)
            redirectAttributes.addAttribute("abilities", form.getAbilities());
        if (form.getPageNumber() > 0)
            redirectAttributes.addAttribute("page", form.getPageNumber());

        return new ModelAndView("redirect:/trabajos");
    }
}
