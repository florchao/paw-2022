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
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.EmployerDto;
import ar.edu.itba.paw.webapp.dto.JobDto;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/employers")
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

    @Context
    private UriInfo uriInfo;

    private final static long PAGE_SIZE = 1;
    private final static long PAGE_SIZE_PROFILE = 2;

    private final static int PAGE_SIZE_REVIEWS = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerController.class);


    @GET
    @Path(value = "/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response employerProfile(@PathParam("id") long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null) {
            HogarUser user = (HogarUser) auth.getPrincipal();
            if (user.getUserID() != id) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        try{
            Employer employer = employerService.getEmployerById(id);
            EmployerDto employerDto = EmployerDto.fromEmployer(uriInfo, employer);
            GenericEntity<EmployerDto> genericEntity = new GenericEntity<EmployerDto>(employerDto){};
            return Response.ok(genericEntity).build();
        }catch (UserNotFoundException u){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/jobs")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response createdJobs(@PathParam("id") long id, @QueryParam("page") @DefaultValue("0") Long page, @QueryParam("profile") String profile, @Context HttpServletRequest request) {
        List<JobDto> jobs = jobService.getUserJobs(id, page, profile != null && profile.equals("true") ? PAGE_SIZE_PROFILE : PAGE_SIZE)
                .stream()
                .map(job -> JobDto.fromCreated(uriInfo, job, request.getHeader("Accept-Language"))).
                collect(Collectors.toList());

        if(profile != null && profile.equals("true")){
            GenericEntity<List<JobDto>> genericEntity = new GenericEntity<List<JobDto>>(jobs){};
            return Response.ok(genericEntity).build();
        }

        int pages = jobService.getMyJobsPageNumber(id, PAGE_SIZE);

        GenericEntity<List<JobDto>> genericEntity = new GenericEntity<List<JobDto>>(jobs){};
        return Response.status(jobs.isEmpty()? Response.Status.NO_CONTENT: Response.Status.OK).entity(genericEntity).header("Access-Control-Expose-Headers", "X-Total-Count").header("X-Total-Count", pages).build();
    }

    @GET
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getEmployerReviews(@PathParam("id") long id, @QueryParam("page") @DefaultValue("0") Long page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        HogarUser principal = (HogarUser) auth.getPrincipal();

        List<ReviewDto> reviews = reviewService.getAllReviewsEmployer(auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))? principal.getUserID() : null, id, page, PAGE_SIZE_REVIEWS)
                .stream()
                .map(r -> ReviewDto.fromEmployerReview(uriInfo, r))
                .collect(Collectors.toList());
        GenericEntity<List<ReviewDto>> genericEntity = new GenericEntity<List<ReviewDto>>(reviews) {
        };
        int pages = reviewService.getPageNumberEmployer(auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))? principal.getUserID() : null, id, PAGE_SIZE_REVIEWS);
        return Response.status(reviews.isEmpty()? Response.Status.NO_CONTENT: Response.Status.OK).entity(genericEntity).header("Access-Control-Expose-Headers", "X-Total-Count").header("X-Total-Count", pages).build();
    }

    @GET
    @Path("/{employerId}/reviews/{employeeId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getMyReview(@PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId, @QueryParam("type") String type) {
        Optional<ReviewDto> myReview = reviewService.getMyReviewEmployer(employeeId, employerId).map(r -> ReviewDto.fromEmployerReview(uriInfo, r));

        if (!myReview.isPresent())
            return Response.noContent().build();
        GenericEntity<ReviewDto> genericEntity = new GenericEntity<ReviewDto>(myReview.get()) {
        };
        return Response.ok(genericEntity).build();
    }


    @POST
    @Path("")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA, })
    public Response createEmployer(@FormDataParam("mail") String mail,
                                   @FormDataParam("password") String password,
                                   @FormDataParam("confirmPassword") String confirmPassword,
                                   @FormDataParam("name") String name,
                                   @FormDataParam("last") String lastName,
                                   @FormDataParam("image") InputStream image
                                   ) throws UserFoundException, PassMatchException {

        if(!mail.matches("[\\w-+_.]+@([\\w]+.)+[\\w]{1,100}") || mail.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty() || !confirmPassword.equals(password) ||
                name.length() > 100 || !name.matches("[a-zA-z\\s'-]+|^$") || name.isEmpty() ||
                lastName.length() > 100 || !lastName.matches("[a-zA-z\\s'-]+|^$") || lastName.isEmpty()
                || image==null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final User u;
        try {
            u = userService.create(mail, password, confirmPassword, 2);
            String fullName = name + " " + lastName;
            employerService.create(fullName.toLowerCase(), u, IOUtils.toByteArray(image));
        } catch (Exception ex){
            ex.printStackTrace();
            return Response.status(Response.Status.CONFLICT).build();
        }
        LOGGER.debug(String.format("employer created under userid %d", u.getId()));

        return Response.status(Response.Status.CREATED).entity(uriInfo.getAbsolutePathBuilder().replacePath("/api/employers").path(String.valueOf(u.getId())).build()).build();
    }
}
