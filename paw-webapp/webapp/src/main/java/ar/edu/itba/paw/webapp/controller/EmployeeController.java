package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.ApplicantDto;
import ar.edu.itba.paw.webapp.dto.ContactDto;
import ar.edu.itba.paw.webapp.dto.EmployeeDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/employees")
@Component
public class EmployeeController {
    private final int PAGE_SIZE = 3;

    private final int PAGE_SIZE_REVIEWS = 1;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private RaitingService ratingService;

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private ReviewService reviewService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @GET
    @Path("")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response filterEmployees(
            @QueryParam("name") String name,
            @QueryParam("experience") Long experienceYears,
            @QueryParam("location") String location,
            @QueryParam("availability") String availability,
            @QueryParam("abilities") String abilities,
            @QueryParam("page") @DefaultValue("0") Long page,
            @QueryParam("order") String orderCriteria,
            @Context HttpServletRequest request
    ) {
        if (experienceYears != null && (experienceYears < 0 || experienceYears > 100)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (location != null && (!location.matches("[1-4][,[1-4]]+") || location.length() > 7)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (availability != null && (!availability.matches("[1-3][,[1-3]]+") || availability.length() > 5))
            return Response.status(Response.Status.BAD_REQUEST).build();
        if (abilities != null && (!abilities.matches("[1-6][,[1-6]]+") || abilities.length() > 11))
            return Response.status(Response.Status.BAD_REQUEST).build();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HogarUser hogarUser;

        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
            System.out.println("EMPLOYER");
            hogarUser = (HogarUser) auth.getPrincipal();
        } else {
            hogarUser = null;
        }

        List<EmployeeDto> employees = employeeService.getFilteredEmployees(name, experienceYears, location, availability, abilities, page, PAGE_SIZE, orderCriteria).stream().map(employee ->
        {
            float rating = ratingService.getRating(employee.getId().getId());
            if (hogarUser != null) {
                Boolean hasContact = !contactService.existsContact(employee.getId().getId(), hogarUser.getUserID()).isEmpty();
                return EmployeeDto.fromExploreContact(uriInfo, employee, rating, request.getHeader("Accept-Language"), hasContact);
            } else
                return EmployeeDto.fromExploreRating(uriInfo, employee, rating, request.getHeader("Accept-Language"));
        }).collect(Collectors.toList());
        int pages = employeeService.getPageNumber(name, experienceYears, location, availability, abilities, PAGE_SIZE, orderCriteria);
        GenericEntity<List<EmployeeDto>> genericEntity = new GenericEntity<List<EmployeeDto>>(employees) {
        };
        return Response.ok(genericEntity).header("Access-Control-Expose-Headers", "X-Total-Count").header("X-Total-Count", pages).build();
    }

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
            return Response.noContent().build();
        }

        EmployeeDto dto;
        if (edit != null && edit.equals("true"))
            dto = EmployeeDto.fromEdit(uriInfo, employee);
        else if (user != null)
            dto = EmployeeDto.fromMyProfile(uriInfo, employee, request.getHeader("Accept-Language"));
        else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
            HogarUser hogarUser = (HogarUser) auth.getPrincipal();
            Boolean hasContact = !contactService.existsContact(employee.getId().getId(), hogarUser.getUserID()).isEmpty();
            dto = EmployeeDto.fromProfile(uriInfo, employee, request.getHeader("Accept-Language"), false, hasContact);
        } else {
            dto = EmployeeDto.fromProfile(uriInfo, employee, request.getHeader("Accept-Language"), true, false);
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

        List<ApplicantDto> list = applicantService.getAppliedJobsByApplicant(id, page, PAGE_SIZE)
                .stream()
                .map(applicant -> ApplicantDto.fromEmployee(uriInfo, applicant, request.getHeader("Accept-Language")))
                .collect(Collectors.toList());
        int pages = applicantService.getPageNumberForAppliedJobs(id, PAGE_SIZE);
        GenericEntity<List<ApplicantDto>> genericEntity = new GenericEntity<List<ApplicantDto>>(list) {
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
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response createEmployee(@FormDataParam("mail") String mail,
                                   @FormDataParam("password") String password,
                                   @FormDataParam("confirmPassword") String confirmPassword,
                                   @FormDataParam("name") String name,
                                   @FormDataParam("location") String location,
                                   @FormDataParam("experienceYears") long experienceYears,
                                   @FormDataParam("hourlyFee") long hourlyFee,
                                   @FormDataParam("availabilities[]") List<String> availabilities,
                                   @FormDataParam("abilities[]") List<String> abilities,
                                   @FormDataParam("image") InputStream image) throws UserFoundException, PassMatchException {
        if (!mail.matches("[\\w-+_.]+@([\\w]+.)+[\\w]{1,100}") || mail.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty() || !confirmPassword.equals(password) ||
                name.length() > 100 || !name.matches("[a-zA-z\\s'-]+|^$") || name.isEmpty() ||
                experienceYears < 0 || experienceYears > 100 || hourlyFee == 0 || hourlyFee < 0 ||
                location.length() > 1 || !location.matches("[1-4]") ||
                availabilities.isEmpty() || abilities.isEmpty() ||
                image == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User u;
        try {
            u = userService.create(mail, password, password, 1);
            employeeService.create(name, location.toLowerCase(), u.getId(), fromListToString(availabilities), experienceYears, hourlyFee, fromListToString(abilities), IOUtils.toByteArray(image));
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.status(Response.Status.CREATED).entity(uriInfo.getAbsolutePathBuilder().replacePath("/api/employees").path(String.valueOf(u.getId())).build()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response editEmployee(@FormDataParam("name") String name,
                                 @FormDataParam("location") String location,
                                 @FormDataParam("experienceYears") long experienceYears,
                                 @FormDataParam("hourlyFee") long hourlyFee,
                                 @FormDataParam("availabilities[]") List<String> availabilities,
                                 @FormDataParam("abilities[]") List<String> abilities,
                                 @FormDataParam("image") InputStream image,
                                 @PathParam("id") long id) throws IOException, UserFoundException, PassMatchException {
        if (name.length() > 100 || !name.matches("[a-zA-z\\s'-]+|^$") || name.isEmpty() ||
                experienceYears < 0 || experienceYears > 100 || hourlyFee == 0 || hourlyFee < 0 ||
                location.length() > 1 || !location.matches("[1-4]") ||
                availabilities.isEmpty() || abilities.isEmpty() ||
                image == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        HogarUser user = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getUserID() != id) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        employeeService.editProfile(name.toLowerCase(), location.toLowerCase(), id, fromListToArray(availabilities), experienceYears, hourlyFee, fromListToArray(abilities), IOUtils.toByteArray(image));
        LOGGER.debug(String.format("updated profile for userid %d", id));

        return Response.ok(id).build();
    }

    private String fromListToString(List<String> arr) {
        StringBuilder ret = new StringBuilder();
        for (String str : arr) {
            ret.append(str).append(",");
        }
        return ret.substring(0, ret.length() - 1);
    }

    private String[] fromListToArray(List<String> arr) {
        String[] str = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            str[i] = arr.get(i);
        }
        return str;
    }
}
