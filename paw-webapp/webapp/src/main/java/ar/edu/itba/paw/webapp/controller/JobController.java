package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.service.ApplicantService;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.form.JobForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.util.*;

@Controller
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private ReviewService reviewService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);
    private final static long PAGE_SIZE_JOBS = 9;
    private static final long PAGE_SIZE = 8;

    private final static int PAGE_SIZE_REVIEWS = 2;

    @RequestMapping(value = "/crearTrabajo", method = {RequestMethod.GET})
    public ModelAndView crearTrabajo(@ModelAttribute("jobForm")final JobForm form){
        return new ModelAndView("createJob");
    }

    @RequestMapping(value = "/createJob", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute("jobForm") final JobForm form, final BindingResult errors){
        if(!errors.hasErrors()) {
            HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Job job = jobService.create(form.getTitle(), form.getLocation(), principal.getUserID(), form.getAvailability(), form.getExperienceYears(), form.fromArrtoString(form.getAbilities()), form.getDescription());
                LOGGER.debug(String.format("job created under jobid %d", job.getJobId()));
                return new ModelAndView("redirect:/trabajo/" + job.getJobId());
        }
        LOGGER.debug("couldn't create job");
        return crearTrabajo(form);
    }

    @RequestMapping(value = "/misTrabajos", method = {RequestMethod.GET})
    public ModelAndView verTrabajos(@RequestParam(value = "publishedPage", required = false) Long page){
        ModelAndView mav = new ModelAndView("publishedJobs");
        if (page == null)
            page = 0L;
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Job> jobs = jobService.getUserJobs(principal.getUserID(), page, PAGE_SIZE);
        List<Job> jobList = new ArrayList<>();
            for (Job job : jobs) {
                job.firstWordsToUpper();
                job.locationNameToUpper();
                jobList.add(job);
            }
        mav.addObject("JobList", jobList);
        mav.addObject("publishedPage", page);
        mav.addObject("publishedMaxPage",jobService.getMyJobsPageNumber(principal.getUserID(), PAGE_SIZE));
        return mav;
}

    @RequestMapping(value = "/trabajo/{id}", method = {RequestMethod.GET})
    public ModelAndView verTrabajo(@PathVariable final long id, @RequestParam(value = "status", required = false) String status, @ModelAttribute("reviewForm") final ReviewForm reviewForm,
                                   @RequestParam(value = "page", required = false) Long page){
        ModelAndView mav = new ModelAndView("viewJob");
        Optional<Job> job = jobService.getJobByID(id);
        int jobStatus = -1;
        Employer employer = null;
        if (job.isPresent()) {
            job.get().employerNameToUpper(job.get().getEmployerId());
            job.get().firstWordsToUpper();
            job.get().locationNameToUpper();
            mav.addObject("job", job.get());
            employer = job.get().getEmployerId();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HogarUser principal = (HogarUser) auth.getPrincipal();
        if (page == null)
            page = 0L;
        List<Review> reviews;
        int maxPage;
        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))){
            Optional<Review> myReview = reviewService.getMyReviewEmployer(principal.getUserID(), employer.getId().getId());
            if (myReview.isPresent()) {
                myReview.get().getEmployeeId().firstWordsToUpper();
                mav.addObject("myReview", myReview.get());
            }
            reviews = reviewService.getAllReviewsEmployer(principal.getUserID(), employer.getId().getId(), page, PAGE_SIZE_REVIEWS);
            maxPage = reviewService.getPageNumberEmployer(principal.getUserID(), employer.getId().getId(), PAGE_SIZE_REVIEWS);
        } else {
            maxPage = reviewService.getPageNumberEmployer(null, employer.getId().getId(), PAGE_SIZE_REVIEWS);
            reviews = reviewService.getAllReviewsEmployer(null, employer.getId().getId(), page, PAGE_SIZE_REVIEWS);
        }
        for (Review rev : reviews) {
            rev.getEmployeeId().firstWordsToUpper();
        }
        mav.addObject("ReviewList", reviews);
        mav.addObject("page", page);
        mav.addObject("maxPage", maxPage);
        Boolean existsApplied = jobService.alreadyApplied(id, principal.getUserID());
        if(existsApplied && job.isPresent()){
            jobStatus = applicantService.getStatus(principal.getUserID(), job.get().getJobId());
        }
        mav.addObject("alreadyApplied", jobStatus);
        mav.addObject("status", status);
        return mav;
    }

    @RequestMapping(value = "addReviewEmployer/{jobId}/{employerId}", method = {RequestMethod.POST})
    public ModelAndView addReview(@ModelAttribute("reviewForm") final ReviewForm reviewForm, @RequestParam(value = "status", required = false) String status, final BindingResult errors, @PathVariable final long jobId, @PathVariable final long employerId){
        if(errors.hasErrors())
            return verTrabajo(jobId,status, reviewForm, null);
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reviewService.create(principal.getUserID(), employerId, reviewForm.getContent(), new Date(System.currentTimeMillis()), false);
        return new ModelAndView("redirect:/trabajo/" + jobId);
    }

    @RequestMapping(value = "/trabajos", method = {RequestMethod.GET})
    public ModelAndView searchJobs(
            @ModelAttribute("filterJobsBy") FilterForm jobForm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "experienceYears", required = false) Long experienceYears,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "availability", required = false) String availability,
            @RequestParam(value = "abilities", required = false) String abilities,
            @RequestParam(value = "page", required = false) Long page) {
        Authentication authority = SecurityContextHolder.getContext().getAuthentication();
        HogarUser user = (HogarUser) authority.getPrincipal();
        if (page == null)
            page = 0L;
        Map<Job, Integer> jobList = new LinkedHashMap<>();
        List<Job> opJob = jobService.getFilteredJobs(name, experienceYears, location, availability, abilities, page, PAGE_SIZE_JOBS);
        for (Job job : opJob) {
            Boolean applied = jobService.alreadyApplied(job.getJobId(), user.getUserID());
            job.firstWordsToUpper();
            job.locationNameToUpper();
            if(applied) {
                int status = applicantService.getStatus(user.getUserID(), job.getJobId());
                jobList.put(job, status);
            }
            else {
                jobList.put(job, -1);
            }
        }
        final ModelAndView mav = new ModelAndView("searchJobs");
        mav.addObject("jobList", jobList);
        mav.addObject("page", page);
        mav.addObject("maxPage", jobService.getPageNumber(name, experienceYears, location, availability, abilities, PAGE_SIZE_JOBS));
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

    @RequestMapping(value = "/deleteJob/{jobId}", method = {RequestMethod.POST, RequestMethod.DELETE})
    public ModelAndView deleteJob(@PathVariable final long jobId){
        jobService.deleteJob(jobId);
        return new ModelAndView("redirect:/misTrabajos");
    }

    @RequestMapping(value = "/closeJob/{jobId}", method = {RequestMethod.POST})
    public ModelAndView closeJob(@PathVariable final long jobId){
        jobService.closeJob(jobId);
        return new ModelAndView("redirect:/trabajo/" + jobId);
    }

    @RequestMapping(value = "/openJob/{jobId}", method = {RequestMethod.POST})
    public ModelAndView openJob(@PathVariable final long jobId){
        jobService.openJob(jobId);
        return new ModelAndView("redirect:/trabajo/" + jobId);
    }
}
