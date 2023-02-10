package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.EmployerService;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.EmployerDto.EmployerCreateDto;
import ar.edu.itba.paw.webapp.dto.EmployerDto.EmployerDto;
import ar.edu.itba.paw.webapp.dto.JobDto.CreatedJobsDto;
import ar.edu.itba.paw.webapp.dto.ReviewDto.EmployerReviewsDto;
import ar.edu.itba.paw.webapp.helpers.UriHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/employers")
@Component
public class EmployerController {

    @Autowired
    private EmployerService employerService;
    @Autowired
    private UserService userService;
    @Autowired
    private JobService jobService;
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UriHelper uriHelper;

    @Context
    private UriInfo uriInfo;

    private final static long PAGE_SIZE = 7;
    private final static long PAGE_SIZE_PROFILE = 2;

    private final static int PAGE_SIZE_REVIEWS = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerController.class);


    @GET
    @Path(value = "/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response employerProfile(@PathParam("id") long id) {
        try {
            Employer employer = employerService.getEmployerById(id);
            EmployerDto employerDto = EmployerDto.fromEmployer(uriInfo, employer);
            GenericEntity<EmployerDto> genericEntity = new GenericEntity<EmployerDto>(employerDto) {
            };
            return Response.ok(genericEntity).build();
        } catch (UserNotFoundException u) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/jobs")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response createdJobs(@PathParam("id") long id, @QueryParam("page") @DefaultValue("0") Long page, @QueryParam("profile") String profile, @Context HttpServletRequest request) {

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);

        List<CreatedJobsDto> jobs = jobService.getUserJobs(id, page, profile != null && profile.equals("true") ? PAGE_SIZE_PROFILE : PAGE_SIZE)
                .stream()
                .map(job -> CreatedJobsDto.fromCreated(uriInfo, job)).
                collect(Collectors.toList());

        int pages = jobService.getMyJobsPageNumber(id, PAGE_SIZE);

        GenericEntity<List<CreatedJobsDto>> genericEntity = new GenericEntity<List<CreatedJobsDto>>(jobs) { };
        Response.ResponseBuilder responseBuilder = Response.status(jobs.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK).entity(genericEntity);
        uriHelper.addPaginationLinks(responseBuilder, uriInfo, page, pages);
        if(profile != null && profile.equals("true"))
            return Response.status(jobs.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK).entity(genericEntity).build();
        return responseBuilder
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .header("X-Total-Count", pages)
                .build();
    }

    @GET
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getEmployerReviews(@PathParam("id") long id, @QueryParam("page") @DefaultValue("0") Long page, @QueryParam("except") Long except) {

        List<EmployerReviewsDto> reviews = reviewService.getAllReviewsEmployer(except, id, page, PAGE_SIZE_REVIEWS)
                .stream()
                .map(r -> EmployerReviewsDto.fromEmployerReview(uriInfo, r))
                .collect(Collectors.toList());
        GenericEntity<List<EmployerReviewsDto>> genericEntity = new GenericEntity<List<EmployerReviewsDto>>(reviews) { };
        int pages = reviewService.getPageNumberEmployer(except, id, PAGE_SIZE_REVIEWS);
        Response.ResponseBuilder responseBuilder = Response.status(reviews.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK).entity(genericEntity);
        uriHelper.addPaginationLinks(responseBuilder, uriInfo, page, pages);
        return responseBuilder
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .header("X-Total-Count", pages)
                .build();
    }

    @GET
    @Path("/{employerId}/reviews/{employeeId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getMyReview(@PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId, @QueryParam("type") String type) {
        Optional<EmployerReviewsDto> myReview = reviewService.getMyReviewEmployer(employeeId, employerId).map(r -> EmployerReviewsDto.fromEmployerReview(uriInfo, r));

        if (!myReview.isPresent())
            return Response.noContent().build();
        GenericEntity<EmployerReviewsDto> genericEntity = new GenericEntity<EmployerReviewsDto>(myReview.get()) {
        };
        return Response.ok(genericEntity).build();
    }


    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response createEmployer(@Valid EmployerCreateDto employerDto) throws UserFoundException, PassMatchException {

        final User u;
        try {
            u = userService.create(employerDto.getMail(), employerDto.getPassword(), employerDto.getConfirmPassword(), 2);
            String fullName = employerDto.getName() + " " + employerDto.getLastname();
            employerService.create(fullName.toLowerCase(), u);
        } catch (Exception ex) {
            LOGGER.error("an exception occurred:", ex);
            return Response.status(Response.Status.CONFLICT).build();
        }
        LOGGER.debug(String.format("employer created under userid %d", u.getId()));

        return Response.status(Response.Status.CREATED).entity(uriInfo.getBaseUriBuilder().path("/employers").path(String.valueOf(u.getId())).build()).build();
    }
}
