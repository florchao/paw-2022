package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.service.ApplicantService;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.IdsDto;
import ar.edu.itba.paw.webapp.dto.JobDto;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.form.JobForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Path("/api/job")
@Component
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private ReviewService reviewService;

    @Context
    UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);
    private final static long PAGE_SIZE_JOBS = 9;
    private static final long PAGE_SIZE = 8;

    private final static int PAGE_SIZE_REVIEWS = 2;

    @GET
    @Path("/ids")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getIds() {
        GenericEntity<IdsDto> genericEntity = new GenericEntity<IdsDto>(IdsDto.fromForm(Availability.getIds(), Abilities.getIds())){};
        return Response.ok(genericEntity).build();
    }

    @POST
    @Path("/")
    @Consumes(value = { MediaType.APPLICATION_JSON, })
    public Response postJob(@Valid final JobForm form) {
        //TODO: poner el id del empleador que esta iniciado sesión
        Job job = jobService.create(form.getTitle(), form.getLocation(), 2, form.getAvailability(), form.getExperienceYears(), form.fromArrtoString(form.getAbilities()), form.getDescription());
        LOGGER.debug(String.format("job created under jobid %d", job.getJobId()));
        return Response.ok(job.getJobId()).build();
    }

//    @RequestMapping(value = "/crearTrabajo", method = {RequestMethod.GET})
//    public ModelAndView crearTrabajo(@ModelAttribute("jobForm")final JobForm form){
//        ModelAndView mav = new ModelAndView("createJob");
//        mav.addObject("abilities", Abilities.getIds());
//        mav.addObject("availability", Availability.getIds());
//        return mav;
//    }
//
//    @RequestMapping(value = "/createJob", method = RequestMethod.POST)
//    public ModelAndView create(@Valid @ModelAttribute("jobForm") final JobForm form, final BindingResult errors){
//        if(!errors.hasErrors()) {
//            HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                Job job = jobService.create(form.getTitle(), form.getLocation(), principal.getUserID(), form.getAvailability(), form.getExperienceYears(), form.fromArrtoString(form.getAbilities()), form.getDescription());
//                LOGGER.debug(String.format("job created under jobid %d", job.getJobId()));
//                return new ModelAndView("redirect:/trabajo/" + job.getJobId());
//        }
//        LOGGER.debug("couldn't create job");
//        return crearTrabajo(form);
//    }
//
    @GET
    @Path("/{id}/employer")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response createdJobs(@PathParam("id") long id) {
//            @RequestParam(value = "publishedPage", required = false) Long page){
//        if (page == null)
//            page = 0L;
        List<JobDto> jobs = jobService.getUserJobs(id, 0L, PAGE_SIZE).stream().map(job -> JobDto.fromExplore(uriInfo, job)).collect(Collectors.toList());
//        mav.addObject("JobList", jobList);
//        mav.addObject("publishedPage", page);
//        mav.addObject("publishedMaxPage",jobService.getMyJobsPageNumber(principal.getUserID(), PAGE_SIZE));
        GenericEntity<List<JobDto>> genericEntity = new GenericEntity<List<JobDto>>(jobs){};
        return Response.ok(genericEntity).build();
}

    @GET
    @Path("/{userId}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response verTrabajo(@PathParam("userId") long userId) throws JobNotFoundException{
        Job job = null;
        try {
            job = jobService.getJobByID(userId);
        } catch (JobNotFoundException exception){
            exception.getMessage();
            exception.getCause();
        }
        if (job == null ){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        JobDto jobDto = JobDto.fromJob(uriInfo, job);
        int jobStatus = -1;
        GenericEntity<JobDto> genericEntity = new GenericEntity<JobDto>(jobDto){};
        return Response.ok(genericEntity).build();
//        Employer employer = job.getEmployerId();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        HogarUser principal = (HogarUser) auth.getPrincipal();
//        if (page == null)
//            page = 0L;
//        List<Review> reviews;
//        int maxPage;
//        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))){
//            Optional<Review> myReview = reviewService.getMyReviewEmployer(principal.getUserID(), employer.getId().getId());
//            if (myReview.isPresent()) {
//                myReview.get().getEmployeeId().firstWordsToUpper();
//                mav.addObject("myReview", myReview.get());
//            }
//            reviews = reviewService.getAllReviewsEmployer(principal.getUserID(), employer.getId().getId(), page, PAGE_SIZE_REVIEWS);
//            maxPage = reviewService.getPageNumberEmployer(principal.getUserID(), employer.getId().getId(), PAGE_SIZE_REVIEWS);
//        } else {
//            maxPage = reviewService.getPageNumberEmployer(null, employer.getId().getId(), PAGE_SIZE_REVIEWS);
//            reviews = reviewService.getAllReviewsEmployer(null, employer.getId().getId(), page, PAGE_SIZE_REVIEWS);
//        }
//        for (Review rev : reviews) {
//            rev.getEmployeeId().firstWordsToUpper();
//        }
//        mav.addObject("ReviewList", reviews);
//        mav.addObject("page", page);
//        mav.addObject("maxPage", maxPage);
//        Boolean existsApplied = jobService.alreadyApplied(id, principal.getUserID());
//        if(existsApplied){
//            jobStatus = applicantService.getStatus(principal.getUserID(), job.getJobId());
//        }
//        mav.addObject("abilities", Abilities.getIds());
//        mav.addObject("availability", Availability.getIds());
//        mav.addObject("alreadyApplied", jobStatus);
//        mav.addObject("status", status);
//        return mav;
    }

//    @RequestMapping(value = "addReviewEmployer/{jobId}/{employerId}", method = {RequestMethod.POST})
//    public ModelAndView addReview(@ModelAttribute("reviewForm") final ReviewForm reviewForm, @RequestParam(value = "status", required = false) String status, final BindingResult errors, @PathVariable final long jobId, @PathVariable final long employerId) throws JobNotFoundException {
//        if(errors.hasErrors())
//            return verTrabajo(jobId,status, reviewForm, null);
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        reviewService.create(principal.getUserID(), employerId, reviewForm.getContent(), new Date(System.currentTimeMillis()), false);
//        return new ModelAndView("redirect:/trabajo/" + jobId);
//    }

//    @RequestMapping(value = "/trabajos", method = {RequestMethod.GET})
//    public ModelAndView searchJobs(
//            @ModelAttribute("filterJobsBy") FilterForm jobForm,
//            @RequestParam(value = "name", required = false) String name,
//            @RequestParam(value = "experienceYears", required = false) Long experienceYears,
//            @RequestParam(value = "location", required = false) String location,
//            @RequestParam(value = "availability", required = false) String availability,
//            @RequestParam(value = "abilities", required = false) String abilities,
//            @RequestParam(value = "page", required = false) Long page) {
//        Authentication authority = SecurityContextHolder.getContext().getAuthentication();
//        HogarUser user = (HogarUser) authority.getPrincipal();
//        if (page == null)
//            page = 0L;
//        Map<Job, Integer> jobList = new LinkedHashMap<>();
//        List<Job> opJob = jobService.getFilteredJobs(name, experienceYears, location, availability, abilities, page, PAGE_SIZE_JOBS);
//        for (Job job : opJob) {
//            Boolean applied = jobService.alreadyApplied(job.getJobId(), user.getUserID());
//            job.firstWordsToUpper();
//            job.locationNameToUpper();
//            if(applied) {
//                int status = applicantService.getStatus(user.getUserID(), job.getJobId());
//                jobList.put(job, status);
//            }
//            else {
//                jobList.put(job, -1);
//            }
//        }
//        final ModelAndView mav = new ModelAndView("searchJobs");
//        mav.addObject("jobList", jobList);
//        mav.addObject("page", page);
//        mav.addObject("maxPage", jobService.getPageNumber(name, experienceYears, location, availability, abilities, PAGE_SIZE_JOBS));
//        mav.addObject("abilities", Abilities.getIds());
//        mav.addObject("availability", Availability.getIds());
//        return mav;
//    }
//
//    @RequestMapping(value = "/filterJobs", method = {RequestMethod.GET})
//    public ModelAndView filterEmployees(@Valid @ModelAttribute("filterJobsBy") FilterForm form, final BindingResult errors, RedirectAttributes redirectAttributes) {
//        if (errors.hasErrors()) {
//            return searchJobs(null, null,null,null,null,null,null);
//        }
//        if (!Objects.equals(form.getName(),""))
//            redirectAttributes.addAttribute("name",form.getName());
//        if (form.getExperienceYears() > 0)
//            redirectAttributes.addAttribute("experienceYears", form.getExperienceYears());
//        if (!Objects.equals(form.getLocation(), ""))
//            redirectAttributes.addAttribute("location", form.getLocation());
//        if (form.getAvailability() != null && form.getAvailability().length > 0)
//            redirectAttributes.addAttribute("availability", form.getAvailability());
//        if (form.getAbilities() != null && form.getAbilities().length > 0)
//            redirectAttributes.addAttribute("abilities", form.getAbilities());
//        if (form.getPageNumber() > 0)
//            redirectAttributes.addAttribute("page", form.getPageNumber());
//
//        return new ModelAndView("redirect:/trabajos");
//    }
//
//    @RequestMapping(value = "/deleteJob/{jobId}", method = {RequestMethod.POST, RequestMethod.DELETE})
//    public ModelAndView deleteJob(@PathVariable final long jobId){
//        jobService.deleteJob(jobId);
//        return new ModelAndView("redirect:/misTrabajos");
//    }
//
//    @RequestMapping(value = "/closeJob/{jobId}", method = {RequestMethod.POST})
//    public ModelAndView closeJob(@PathVariable final long jobId){
//        jobService.closeJob(jobId);
//        return new ModelAndView("redirect:/trabajo/" + jobId);
//    }
//
//    @RequestMapping(value = "/openJob/{jobId}", method = {RequestMethod.POST})
//    public ModelAndView openJob(@PathVariable final long jobId){
//        jobService.openJob(jobId);
//        return new ModelAndView("redirect:/trabajo/" + jobId);
//    }
}
