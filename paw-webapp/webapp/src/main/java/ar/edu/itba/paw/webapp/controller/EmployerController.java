package ar.edu.itba.paw.webapp.controller;

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
import ar.edu.itba.paw.webapp.form.EmployerForm;
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
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
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

    private final static long PAGE_SIZE = 9;
    private final static long PAGE_SIZE_PROFILE = 2;

    private final static int PAGE_SIZE_REVIEWS = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerController.class);


    @GET
    @Path(value = "/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response employerProfile(@PathParam("id") long id) throws UserNotFoundException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null) {
            HogarUser user = (HogarUser) auth.getPrincipal();
            if (user.getUserID() != id) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }

        Optional<EmployerDto> employer = employerService.getEmployerById(id).map(e -> EmployerDto.fromEmployer(uriInfo, e));
        if(employer.isPresent()){
            GenericEntity<EmployerDto> genericEntity = new GenericEntity<EmployerDto>(employer.get()){};
            return Response.ok(genericEntity).build();
        }

//                mav.addObject("page", page);
//                mav.addObject("maxPage",reviewService.getMyProfileReviewsEmployerPageNumber(user.get().getId(), PAGE_SIZE));
//                mav.addObject("ReviewList", myReviews);
//            }
//            return mav;
//        }
//        final ModelAndView mav = new ModelAndView("viewProfile");
//        if (user.isPresent()) {
//            mav.addObject("user", user.get());
//            Optional<Employee> employee = employeeService.getEmployeeById(user.get().getId());
//            if (employee.isPresent()) {
//                employee.get().firstWordsToUpper();
//                employee.get().locationFirstWordsToUpper();
//                String language = LocaleContextHolder.getLocale().getLanguage();
//                employee.get().nameAvailability(language);
//                employee.get().nameAbilities(language);
//                mav.addObject("employee", employee.get());
//            }
//            mav.addObject("userId", user.get().getId());
//            List<Review> myReviews = reviewService.getMyProfileReviews(user.get().getId(), page, PAGE_SIZE);
//            for (Review rev : myReviews) {
//                rev.getEmployerId().firstWordsToUpper();
//            }
//            mav.addObject("myProfileFlag", true);
//            mav.addObject("page", page);
//            mav.addObject("maxPage",reviewService.getMyProfileReviewsPageNumber(user.get().getId(), PAGE_SIZE));
//            mav.addObject("ReviewList", myReviews);
//        }
//        return mav;
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}/jobs")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response createdJobs(@PathParam("id") long id, @QueryParam("page") Long page, @QueryParam("profile") String profile, @Context HttpServletRequest request) {
        if (page == null)
            page = 0L;

        System.out.println("profile: " + profile);

        List<JobDto> jobs = jobService.getUserJobs(id, page, profile != null && profile.equals("true") ? PAGE_SIZE_PROFILE : PAGE_SIZE).stream().map(job -> JobDto.fromCreated(uriInfo, job, request.getHeader("Accept-Language"))).collect(Collectors.toList());

        if(jobs.isEmpty()){
            return Response.noContent().build();
        }

        GenericEntity<List<JobDto>> genericEntity = new GenericEntity<List<JobDto>>(jobs){};
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getEmployerReviews(@PathParam("id") long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        HogarUser principal = (HogarUser) auth.getPrincipal();

        List<ReviewDto> reviews = reviewService.getAllReviewsEmployer(auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))? principal.getUserID() : null, id, 0L, PAGE_SIZE_REVIEWS).stream().map(r -> ReviewDto.fromEmployerReview(uriInfo, r)).collect(Collectors.toList());
        GenericEntity<List<ReviewDto>> genericEntity = new GenericEntity<List<ReviewDto>>(reviews) {
        };
        return Response.ok(genericEntity).build();
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
                                   ) throws UserFoundException, PassMatchException, IOException {

        final User u = userService.create(mail, password, confirmPassword, 2);
        String fullName = name + " " + lastName;
        employerService.create(fullName.toLowerCase(), u, IOUtils.toByteArray(image));
        LOGGER.debug(String.format("employer created under userid %d", u.getId()));

        return Response.status(Response.Status.CREATED).entity(uriInfo.getAbsolutePathBuilder().replacePath("/api/employers").path(String.valueOf(u.getId())).build()).build();
    }
}
