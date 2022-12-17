package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.service.ApplicantService;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.ApplicantDto;
import ar.edu.itba.paw.webapp.dto.JobDto;
import ar.edu.itba.paw.webapp.form.JobForm;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/api/jobs")
@Component
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicantService applicantService;

    @Context
    UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);
    private static final int PAGE_SIZE = 9;


    @GET
    @Path("")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response filterJobs(
            @QueryParam("name") String name,
            @QueryParam("experience") Long experienceYears,
            @QueryParam("location") String location,
            @QueryParam("availability") String availability,
            @QueryParam("abilities") String abilities,
            @QueryParam("page") @DefaultValue("0") Long page,
            @Context HttpServletRequest request
    ) {

        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<JobDto> jobs = jobService.getFilteredJobs(name, experienceYears, location, availability, abilities, page, PAGE_SIZE).stream().map(j -> {
            int status = applicantService.getStatus(principal.getUserID(), j.getJobId());
            return JobDto.fromExplore(uriInfo, j, status, request.getHeader("Accept-Language"));
        }).collect(Collectors.toList());

        int pages = jobService.getPageNumber(name, experienceYears, location, availability, abilities, PAGE_SIZE);
        GenericEntity<List<JobDto>> genericEntity = new GenericEntity<List<JobDto>>(jobs){};
        return Response.ok(genericEntity).header("Access-Control-Expose-Headers", "X-Total-Count") .header("X-Total-Count", pages).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response verTrabajo(@PathParam("id") long id, @Context HttpServletRequest request) throws JobNotFoundException {
        Job job;
        try {
            job = jobService.getJobByID(id);
        } catch (JobNotFoundException exception) {
            exception.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        JobDto jobDto = JobDto.fromJob(uriInfo, job, request.getHeader("Accept-Language"));

        GenericEntity<JobDto> genericEntity = new GenericEntity<JobDto>(jobDto) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/{jobId}/applicants")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response applicants(@PathParam("jobId") long jobId, @QueryParam("page") @DefaultValue("0") Long page) {

        Job job;
        try {
            job = jobService.getJobByID(jobId);
        } catch (JobNotFoundException exception) {
            exception.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (hogarUser.getUserID() != job.getEmployerId().getId().getId()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<ApplicantDto> list = applicantService.getApplicantsByJob(jobId, page, PAGE_SIZE)
                .stream()
                .map(a -> ApplicantDto.fromJob(uriInfo, a))
                .collect(Collectors.toList());
        int pages = applicantService.getPageNumber(jobId, PAGE_SIZE);
        GenericEntity<List<ApplicantDto>> genericEntity = new GenericEntity<List<ApplicantDto>>(list) {};
        return Response.ok(genericEntity).header("Access-Control-Expose-Headers", "X-Total-Count").header("X-Total-Count", pages).build();
    }


    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response postJob(@Valid final JobForm form) {
        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Job job = jobService.create(form.getTitle(), form.getLocation(), hogarUser.getUserID(), form.getAvailability(), form.getExperienceYears(), form.fromArrtoString(form.getAbilities()), form.getDescription());
        LOGGER.debug(String.format("job created under jobid %d", job.getJobId()));
        return Response.status(Response.Status.CREATED).entity(uriInfo.getAbsolutePathBuilder().replacePath("/api/jobs").path(String.valueOf(job.getJobId())).build()).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response deleteJob(@PathParam("id") long id) {

        Job job;
        try {
            job = jobService.getJobByID(id);
        } catch (JobNotFoundException exception) {
            exception.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (hogarUser.getUserID() != job.getEmployerId().getId().getId()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        jobService.deleteJob(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response updateJobStatus(@PathParam("id") long id,
                                    @FormDataParam("status") Boolean status) throws JobNotFoundException {

        if(Objects.isNull(status))
            return Response.status(Response.Status.BAD_REQUEST).build();

        Job job;
        try {
            job = jobService.getJobByID(id);
        } catch (JobNotFoundException exception) {
            exception.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (hogarUser.getUserID() != job.getEmployerId().getId().getId()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (status) {
            jobService.openJob(id);
        } else {
            jobService.closeJob(id);
        }
        return Response.ok(status).build();
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
}
