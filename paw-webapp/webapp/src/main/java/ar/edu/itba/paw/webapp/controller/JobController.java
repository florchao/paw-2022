package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.form.JobForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);
    private final static long PAGE_SIZE = 8;

    @RequestMapping("/crearTrabajo")
    ModelAndView crearTrabajo(@ModelAttribute("jobForm")final JobForm form){
        return new ModelAndView("createJob");
    }

    @RequestMapping(value = "/createJob", method = RequestMethod.POST)
    ModelAndView create(@Valid @ModelAttribute("jobForm") final JobForm form, final BindingResult errors){
        if(errors.hasErrors()) {
            LOGGER.debug("couldn't create job");
            return crearTrabajo(form);
        }
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Job job = jobService.create(form.getTitle(), form.getLocation(), principal.getUserID(), form.getAvailability(), form.getExperienceYears(), form.fromArrtoString(form.getAbilities()), form.getDescription());
        LOGGER.debug(String.format("job created under jobid %d", job.getJobId()));
        return new ModelAndView("redirect:/trabajo/" + job.getJobId());
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

    @RequestMapping(value = "/trabajos", method = RequestMethod.GET)
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
        if (!Objects.equals(form.getName(),"") && !form.getName().contains("\'") && !form.getName().contains("\""))
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
