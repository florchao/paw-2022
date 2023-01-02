package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.ApplicantDto.JobsAppliedDto;
import ar.edu.itba.paw.webapp.dto.ContactDto.ContactDto;
import ar.edu.itba.paw.webapp.dto.EmployeeDto.EmployeeCreateDto;
import ar.edu.itba.paw.webapp.dto.EmployeeDto.EmployeeDto;
import ar.edu.itba.paw.webapp.dto.EmployeeDto.EmployeeEditDto;
import ar.edu.itba.paw.webapp.dto.ReviewDto.ReviewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/employee")
@Component
public class EmployeeController {
    private final int PAGE_SIZE = 8;

    private final int PAGE_SIZE_REVIEWS = 4;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private ReviewService reviewService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response employeeProfile(@PathParam("id") long id, @QueryParam("edit") String edit, @Context HttpServletRequest request) throws UserNotFoundException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        HogarUser user = null;

        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
            user = (HogarUser) auth.getPrincipal();
            if (user.getUserID() != id) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }

        Employee employee;
        try {
            employee = employeeService.getEmployeeById(id);
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);

        EmployeeDto dto;
        if (edit != null && edit.equals("true"))
            dto = EmployeeDto.fromEdit(uriInfo, employee);
        else if (user != null)
            dto = EmployeeDto.fromMyProfile(uriInfo, employee);
        else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
            HogarUser hogarUser = (HogarUser) auth.getPrincipal();
            Boolean hasContact = !contactService.existsContact(employee.getId().getId(), hogarUser.getUserID()).isEmpty();
            dto = EmployeeDto.fromProfile(uriInfo, employee, false, hasContact);
        } else {
            dto = EmployeeDto.fromProfile(uriInfo, employee, true, false);
        }

        GenericEntity<EmployeeDto> genericEntity = new GenericEntity<EmployeeDto>(dto) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("{id}/jobs")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response appliedTo(@QueryParam("page") @DefaultValue("0") Long page, @PathParam("id") long id, @Context HttpServletRequest request) {
        HogarUser user = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getUserID() != id) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);

        List<JobsAppliedDto> list = applicantService.getAppliedJobsByApplicant(id, page, PAGE_SIZE)
                .stream()
                .map(applicant -> JobsAppliedDto.fromEmployee(uriInfo, applicant))
                .collect(Collectors.toList());
        int pages = applicantService.getPageNumberForAppliedJobs(id, PAGE_SIZE);
        GenericEntity<List<JobsAppliedDto>> genericEntity = new GenericEntity<List<JobsAppliedDto>>(list) {
        };
        return Response.status(list.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK).entity(genericEntity).header("Access-Control-Expose-Headers", "X-Total-Count").header("X-Total-Count", pages).build();
    }

    @GET
    @Path("/{id}/contacts")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response employeeContacts(@PathParam("id") long id, @QueryParam("page") @DefaultValue("0") Long page) throws UserNotFoundException {

        HogarUser user = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getUserID() != id) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<ContactDto> contacts = new ArrayList<>(contactService.getAllContacts(id, page, PAGE_SIZE)).stream().map(c -> ContactDto.fromContact(uriInfo, c)).collect(Collectors.toList());
        int pages = contactService.getPageNumber(id, PAGE_SIZE);
        GenericEntity<List<ContactDto>> genericEntity = new GenericEntity<List<ContactDto>>(contacts) {
        };
        return Response.status(contacts.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK).entity(genericEntity).header("Access-Control-Expose-Headers", "X-Total-Count").header("X-Total-Count", pages).build();
    }

    @GET
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getEmployeeReviews(@PathParam("id") long id, @QueryParam("page") @DefaultValue("0") Long page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        HogarUser principal = (HogarUser) auth.getPrincipal();

        List<ReviewDto> reviews = reviewService.getAllReviews(id, auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER")) ? principal.getUserID() : null, page, PAGE_SIZE_REVIEWS)
                .stream()
                .map(r -> ReviewDto.fromEmployeeReview(uriInfo, r))
                .collect(Collectors.toList());
        int pages = reviewService.getPageNumber(id, auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER")) ? principal.getUserID() : null, PAGE_SIZE_REVIEWS);
        GenericEntity<List<ReviewDto>> genericEntity = new GenericEntity<List<ReviewDto>>(reviews) {
        };
        return Response.status(reviews.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK).entity(genericEntity).header("Access-Control-Expose-Headers", "X-Total-Count").header("X-Total-Count", pages).build();
    }

    @GET
    @Path("/{employeeId}/reviews/{employerId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getMyReviewToEmployee(@PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId) {
        Optional<ReviewDto> myReview = reviewService.getMyReview(employeeId, employerId).map(r -> ReviewDto.fromEmployeeReview(uriInfo, r));
        if (!myReview.isPresent())
            return Response.noContent().build();
        GenericEntity<ReviewDto> genericEntity = new GenericEntity<ReviewDto>(myReview.get()) {
        };
        return Response.ok(genericEntity).build();

    }

    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response createEmployee(@Valid EmployeeCreateDto employeeCreateDto) throws UserFoundException, PassMatchException {
//        if (!mail.matches("[\\w-+_.]+@([\\w]+.)+[\\w]{1,100}") || mail.isEmpty() ||
//                password.isEmpty() || confirmPassword.isEmpty() || !confirmPassword.equals(password) ||
//                name.length() > 100 || !name.matches("[a-zA-z\\s'-]+|^$") || name.isEmpty() ||
//                experienceYears < 0 || experienceYears > 100 || hourlyFee == 0 || hourlyFee < 0 ||
//                location.length() > 1 || !location.matches("[1-4]") ||
//                availabilities.isEmpty() || abilities.isEmpty() ||
//                image == null) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }

        User u;
        try {
            u = userService.create(employeeCreateDto.getMail(), employeeCreateDto.getPassword(), employeeCreateDto.getConfirmPassword(), 1);
            employeeService.create(employeeCreateDto.getName(), employeeCreateDto.getLocation(), u.getId(), fromArrayToString(employeeCreateDto.getAvailability()), employeeCreateDto.getExperienceYears(), employeeCreateDto.getHourlyFee(), fromArrayToString(employeeCreateDto.getAbilities()), employeeCreateDto.getImage());
        } catch (Exception ex) {
            LOGGER.error("an exception occurred:", ex);
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.status(Response.Status.CREATED).entity(uriInfo.getBaseUriBuilder().path("/api/employees").path(String.valueOf(u.getId())).build()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response editEmployee(@Valid EmployeeEditDto employeeEditDto,
                                 @PathParam("id") long id) throws UserFoundException, PassMatchException {
//        if (name.length() > 100 || !name.matches("[a-zA-z\\s'-]+|^$") || name.isEmpty() ||
//                experienceYears < 0 || experienceYears > 100 || hourlyFee == 0 || hourlyFee < 0 ||
//                location.length() > 1 || !location.matches("[1-4]") ||
//                availabilities.isEmpty() || abilities.isEmpty() ||
//                image == null) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
        HogarUser user = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getUserID() != id) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        employeeService.editProfile(employeeEditDto.getName(), employeeEditDto.getLocation(), id, employeeEditDto.getAvailability(), employeeEditDto.getExperienceYears(), employeeEditDto.getHourlyFee(), employeeEditDto.getAbilities(), employeeEditDto.getImage());
        LOGGER.debug(String.format("updated profile for userid %d", id));

        return Response.ok(id).build();
    }

    private String fromArrayToString(String[] arr) {
        StringBuilder ret = new StringBuilder();
        for (String str : arr) {
            ret.append(str).append(",");
        }
        return ret.substring(0, ret.length() - 1);
    }
}
