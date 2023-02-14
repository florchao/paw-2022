package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.dto.ApplicantDto.JobsAppliedDto;
import ar.edu.itba.paw.webapp.dto.ContactDto.ContactDto;
import ar.edu.itba.paw.webapp.dto.EmployeeDto.EmployeeCreateDto;
import ar.edu.itba.paw.webapp.dto.EmployeeDto.EmployeeDto;
import ar.edu.itba.paw.webapp.dto.EmployeeDto.EmployeeEditDto;
import ar.edu.itba.paw.webapp.dto.ReviewDto.EmployeeReviewDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.helpers.UriHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/employees")
@Component
public class EmployeeController {
    private final int PAGE_SIZE = 8;

    private final int PAGE_SIZE_REVIEWS = 3;

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

    @Autowired
    private UriHelper uriHelper;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @GET
    @Path("")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response filterEmployees(
            @QueryParam("name") String name,
            @QueryParam("experience") @Min(0) @Max(100) Long experienceYears,
            @QueryParam("location") @Size(max = 7) @Pattern(regexp = "[1-4][,[1-4]]*") String location,
            @QueryParam("availability") @Size(max = 5) @Pattern(regexp = "[1-3][,[1-3]]*") String availability,
            @QueryParam("abilities") @Size(max = 11) @Pattern(regexp = "[1-6][,[1-6]]*") String abilities,
            @QueryParam("page") @DefaultValue("0") Long page,
            @QueryParam("order") String orderCriteria,
            @Context HttpServletRequest request
    ) {
        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);

        List<EmployeeDto> employees = employeeService
                .getFilteredEmployees(name, experienceYears, location, availability, abilities, page, PAGE_SIZE, orderCriteria)
                .stream()
                .map(employee -> EmployeeDto.fromEmployee(uriInfo, employee, false))
                .collect(Collectors.toList());
        int pages = employeeService.getPageNumber(name, experienceYears, location, availability, abilities, PAGE_SIZE, orderCriteria);
        GenericEntity<List<EmployeeDto>> genericEntity = new GenericEntity<List<EmployeeDto>>(employees) {
        };
        Response.ResponseBuilder responseBuilder = Response.ok(genericEntity);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        if (request.getQueryString() != null) {
            UriHelper.fillQueryParams(uriBuilder, name, experienceYears, location, availability, abilities, orderCriteria);
        }
        return uriHelper.addPaginationLinksForExplore(responseBuilder, uriBuilder, page, pages)
                .header("Access-Control-Expose-Headers", "Total-Count")
                .header("Total-Count", pages)
                .build();

    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response employeeProfile(@PathParam("id") long id, @Context HttpServletRequest request) throws UserNotFoundException {

        Employee employee;
        try {
            employee = employeeService.getEmployeeById(id);
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);

        EmployeeDto dto = EmployeeDto.fromEmployee(uriInfo, employee, false);

        GenericEntity<EmployeeDto> genericEntity = new GenericEntity<EmployeeDto>(dto) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/{id}/edit")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response employeeProfileToEdit(@PathParam("id") long id, @Context HttpServletRequest request) throws UserNotFoundException {

        Employee employee;
        try {
            employee = employeeService.getEmployeeById(id);
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);

        EmployeeDto dto = EmployeeDto.fromEdit(uriInfo, employee);

        GenericEntity<EmployeeDto> genericEntity = new GenericEntity<EmployeeDto>(dto) {
        };
        return Response.ok(genericEntity).build();
    }


    @GET
    @Path("{id}/jobs")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response appliedTo(@QueryParam("page") @DefaultValue("0") Long page, @PathParam("id") long id, @Context HttpServletRequest request) {

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);

        List<JobsAppliedDto> list = applicantService.getAppliedJobsByApplicant(id, page, PAGE_SIZE)
                .stream()
                .map(applicant -> JobsAppliedDto.fromEmployee(uriInfo, applicant))
                .collect(Collectors.toList());
        int pages = applicantService.getPageNumberForAppliedJobs(id, PAGE_SIZE);
        GenericEntity<List<JobsAppliedDto>> genericEntity = new GenericEntity<List<JobsAppliedDto>>(list) {
        };
        if (list.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).entity(genericEntity).build();
        Response.ResponseBuilder responseBuilder = Response.ok(genericEntity);
        uriHelper.addPaginationLinks(responseBuilder, uriInfo, page, pages);
        return responseBuilder
                .header("Access-Control-Expose-Headers", "Total-Count")
                .header("Total-Count", pages)
                .build();
    }

    @GET
    @Path("/{id}/contacts")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response employeeContacts(@PathParam("id") long id, @QueryParam("page") @DefaultValue("0") Long page) throws UserNotFoundException {

        List<ContactDto> contacts = new ArrayList<>(contactService.getAllContacts(id, page, PAGE_SIZE)).stream().map(c -> ContactDto.fromContact(uriInfo, c)).collect(Collectors.toList());
        int pages = contactService.getPageNumber(id, PAGE_SIZE);
        GenericEntity<List<ContactDto>> genericEntity = new GenericEntity<List<ContactDto>>(contacts) {
        };
        if (contacts.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).entity(genericEntity).build();
        Response.ResponseBuilder responseBuilder = Response.ok(genericEntity);
        uriHelper.addPaginationLinks(responseBuilder, uriInfo, page, pages);
        return responseBuilder
                .entity(genericEntity)
                .header("Access-Control-Expose-Headers", "Total-Count")
                .header("Total-Count", pages)
                .build();
    }

    @GET
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getEmployeeReviews(@PathParam("id") long id, @QueryParam("page") @DefaultValue("0") Long page, @QueryParam("except") Long except) {

        List<EmployeeReviewDto> reviews = reviewService.getAllReviews(id, except, page, PAGE_SIZE_REVIEWS)
                .stream()
                .map(r -> EmployeeReviewDto.fromEmployeeReview(uriInfo, r))
                .collect(Collectors.toList());
        int pages = reviewService.getPageNumber(id, except, PAGE_SIZE_REVIEWS);
        GenericEntity<List<EmployeeReviewDto>> genericEntity = new GenericEntity<List<EmployeeReviewDto>>(reviews) {
        };
        if (reviews.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).entity(genericEntity).build();
        Response.ResponseBuilder responseBuilder = Response.ok(genericEntity);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriHelper.addPaginationLinksForReviews(responseBuilder, uriBuilder, page, pages, except);
        return responseBuilder
                .entity(genericEntity)
                .header("Access-Control-Expose-Headers", "Total-Count")
                .header("Access-Control-Expose-Headers", "Link")
                .header("Total-Count", pages)
                .build();
    }

    @GET
    @Path("/{employeeId}/reviews/{employerId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getMyReviewToEmployee(@PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId) {
        Optional<EmployeeReviewDto> myReview = reviewService.getMyReview(employeeId, employerId).map(r -> EmployeeReviewDto.fromEmployeeReview(uriInfo, r));
        if (!myReview.isPresent())
            return Response.noContent().build();
        GenericEntity<EmployeeReviewDto> genericEntity = new GenericEntity<EmployeeReviewDto>(myReview.get()) {
        };
        return Response.ok(genericEntity).build();

    }

    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response createEmployee(@Valid EmployeeCreateDto employeeCreateDto) throws UserFoundException, PassMatchException {

        User u;
        try {
            u = userService.create(employeeCreateDto.getMail(), employeeCreateDto.getPassword(), employeeCreateDto.getConfirmPassword(), 1);
            employeeService.create(employeeCreateDto.getName(), employeeCreateDto.getLocation(), u.getId(), fromArrayToString(employeeCreateDto.getAvailability()), employeeCreateDto.getExperienceYears(), employeeCreateDto.getHourlyFee(), fromArrayToString(employeeCreateDto.getAbilities()));
        } catch (Exception ex) {
            LOGGER.error("an exception occurred:", ex);
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.created(uriInfo.getBaseUriBuilder().path("/api/employees").path(String.valueOf(u.getId())).build()).header("Access-Control-Expose-Headers", "Location").build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response editEmployee(@Valid EmployeeEditDto employeeEditDto,
                                 @PathParam("id") long id) throws UserFoundException, PassMatchException {

        employeeService.editProfile(employeeEditDto.getName(), employeeEditDto.getLocation(), id, employeeEditDto.getAvailability(), employeeEditDto.getExperienceYears(), employeeEditDto.getHourlyFee(), employeeEditDto.getAbilities());
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
