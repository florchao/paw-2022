package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Abilities;
import ar.edu.itba.paw.model.Availability;
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
import ar.edu.itba.paw.webapp.form.EmployeeEditForm;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/employees")
@Component
public class EmployeeController {
    private final int PAGE_SIZE = 100;

    private final int PAGE_SIZE_REVIEWS = 5;

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
    @Produces(value = { MediaType.APPLICATION_JSON })
    public Response filterEmployees(
            @QueryParam("name") String name,
            @QueryParam("experience") Long experienceYears,
            @QueryParam("location") String location,
            @QueryParam("availability") String availability,
            @QueryParam("abilities") String abilities,
            @QueryParam("page") Long page,
            @QueryParam("order") String orderCriteria
    ) {

        if (page == null)
            page = 0L;
        List <EmployeeDto> employeeDtos = new ArrayList<>(Collections.emptyList());
        employeeService.getFilteredEmployees(name, experienceYears, location, availability, abilities, page, PAGE_SIZE, orderCriteria).forEach(employee ->
        {
            float rating = ratingService.getRating(employee.getId().getId());
            employeeDtos.add(EmployeeDto.fromExploreRating(uriInfo, employee, rating));
        });
        GenericEntity<List<EmployeeDto>> genericEntity = new GenericEntity<List<EmployeeDto>>(employeeDtos){};
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response employeeProfile(@PathParam("id") long id, @QueryParam("edit") String edit) throws UserNotFoundException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
            HogarUser user = (HogarUser) auth.getPrincipal();
            if (user.getUserID() != id) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }

        Optional<Employee> employee = employeeService.getEmployeeById(id);
        Optional<EmployeeDto> dto;
        if(edit != null && edit.equals("true"))
            dto = employee.map(e -> EmployeeDto.fromEdit(uriInfo, e));
        else
            dto = employee.map(e -> EmployeeDto.fromProfile(uriInfo, e));
        if (employee.isPresent()) {
            GenericEntity<EmployeeDto> genericEntity = new GenericEntity<EmployeeDto>(dto.get()){};
            return Response.ok(genericEntity).build();
        }
        return Response.noContent().build();
//        mav.addObject("status", status);
//        if (page == null)
//            page = 0L;
//        List<Review> reviews;
//        int maxPage;
//        boolean hasAlreadyRated = false;
//        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
//            HogarUser user = (HogarUser) auth.getPrincipal();
//            Boolean exists = contactService.existsContact(userId, user.getUserID());
//            mav.addObject("contacted", exists);
//            Optional<Review> myReview = reviewService.getMyReview(userId, user.getUserID());
//            if (myReview.isPresent()) {
//                myReview.get().getEmployerId().firstWordsToUpper();
//                mav.addObject("myReview", myReview.get());
//            }
//            reviews = reviewService.getAllReviews(userId, user.getUserID(), page, PAGE_SIZE);
//            maxPage = reviewService.getPageNumber(userId, user.getUserID(), PAGE_SIZE);
//            hasAlreadyRated = raitingService.hasAlreadyRated(userId, user.getUserID());
//        } else {
//            maxPage = reviewService.getPageNumber(userId, null, PAGE_SIZE);
//            reviews = reviewService.getAllReviews(userId, null, page, PAGE_SIZE);
//        }
//        for (Review rev : reviews) {
//            rev.getEmployerId().firstWordsToUpper();
//        }
//        mav.addObject("alreadyRated", hasAlreadyRated);
//        mav.addObject("rating", employeeService.getRating(userId));
//        mav.addObject("voteCount", employeeService.getRatingVoteCount(userId));
//        mav.addObject("ReviewList", reviews);
//        mav.addObject("page", page);
//        mav.addObject("maxPage", maxPage);
    }

    @GET
    @Path("{id}/jobs")
    @Produces(value = { MediaType.APPLICATION_JSON })
    public Response appliedTo(@QueryParam("page") Long page, @PathParam("id") long id){
//        if (page == null)
//            page = 0L;
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ApplicantDto> list = applicantService.getAppliedJobsByApplicant(id, 0L, PAGE_SIZE).stream().map(applicant -> ApplicantDto.fromEmployee(uriInfo, applicant)).collect(Collectors.toList());
        GenericEntity<List<ApplicantDto>> genericEntity = new GenericEntity<List<ApplicantDto>>(list){};
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/{id}/contacts")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response employeeContacts(@PathParam("id") long id) throws UserNotFoundException {
        List<ContactDto> contacts = new ArrayList<>(contactService.getAllContacts(id, 0L, PAGE_SIZE)).stream().map(c -> ContactDto.fromContact(uriInfo, c)).collect(Collectors.toList());
        GenericEntity<List<ContactDto>> genericEntity = new GenericEntity<List<ContactDto>>(contacts) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getEmployeeReviews(@PathParam("id") long id) {
        List<ReviewDto> reviews = reviewService.getAllReviews(id, null, 0L, PAGE_SIZE_REVIEWS).stream().map(r -> ReviewDto.fromEmployeeReview(uriInfo, r)).collect(Collectors.toList());

        //TODO: si es empleado el que inició sesión
        GenericEntity<List<ReviewDto>> genericEntity = new GenericEntity<List<ReviewDto>>(reviews) {
        };
        return Response.ok(genericEntity).build();
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
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA, })
    public Response createEmployee(@FormDataParam("mail") String mail,
                                   @FormDataParam("password") String password,
                                   @FormDataParam("confirmPassword") String confirmPassword,
                                   @FormDataParam("name") String name,
                                   @FormDataParam("location") String location,
                                   @FormDataParam("experienceYears") long experienceYears,
                                   @FormDataParam("availabilities[]") List<String> availabilities,
                                   @FormDataParam("abilities[]") List<String> abilities,
                                   @FormDataParam("image") InputStream image) throws IOException, UserFoundException, PassMatchException {
        User u = userService.create(mail, password, password, 1);

        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uid = String.valueOf(hogarUser.getUserID());

        employeeService.create(name, location.toLowerCase(), u.getId(), fromListToString(availabilities), experienceYears, fromListToString(abilities), IOUtils.toByteArray(image));
        return Response.ok(u.getId()).build();
    }

    //TODO: PUT y DELETE? de employee. El delete esta en el user controller porque es el mismo para employer
    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA, })
    public Response editEmployee(@FormDataParam("name") String name,
                                  @FormDataParam("location") String location,
                                  @FormDataParam("experienceYears") long experienceYears,
                                  @FormDataParam("availabilities[]") List<String> availabilities,
                                  @FormDataParam("abilities[]") List<String> abilities,
                                  @FormDataParam("image") InputStream image,
                                  @PathParam("id") long id) throws IOException, UserFoundException, PassMatchException {

        employeeService.editProfile(name.toLowerCase(), location.toLowerCase(), id, fromListToArray(availabilities), experienceYears, fromListToArray(abilities), IOUtils.toByteArray(image));
        LOGGER.debug(String.format("updated profile for userid %d", id));

        return Response.ok(id).build();
    }


//    @RequestMapping(value = "/editarPerfil", method = {RequestMethod.GET})
//    public ModelAndView editProfile(@ModelAttribute("employeeEditForm") final EmployeeEditForm form) throws UserNotFoundException {
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Optional<Employee> employee = employeeService.getEmployeeById(principal.getUserID());
//        final ModelAndView mav = new ModelAndView("editProfile");
//        mav.addObject("abilities", Abilities.getIds());
//        mav.addObject("availability", Availability.getIds());
//        if(employee.isPresent()) {
//            form.setAbilities(new String[]{String.join(", ", employee.get().getAbilitiesArr())});
//            form.setAbilities(employee.get().getAbilitiesArr().toArray(new String[0]));
//            form.setAvailability(employee.get().getAvailabilityArr().toArray(new String[0]));
//            form.setLocation(employee.get().getLocation());
//            form.setName(employee.get().getName());
//            form.setExperienceYears(employee.get().getExperienceYears());
//        }
//        mav.addObject("userId", principal.getUserID());
//        return mav;
//    }

//    @RequestMapping(value = "/editEmployee", method = {RequestMethod.POST})
//    public ModelAndView edit(@Valid @ModelAttribute("employeeEditForm") final EmployeeEditForm form, final BindingResult errors) throws UserNotFoundException {
//
//        if(errors.hasErrors()) {
//            LOGGER.debug("Could not update profile");
//            return editProfile(form);
//        }
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        employeeService.editProfile(form.getName().toLowerCase(), form.getLocation().toLowerCase(), (principal.getUserID()), form.getAvailability(), form.getExperienceYears(), form.getAbilities(), form.getImage().getBytes());
//        LOGGER.debug(String.format("updated profile for userid %d", principal.getUserID()));
//        return new ModelAndView("redirect:/verPerfil/");
//    }

    private String fromListToString(List<String> arr){
        StringBuilder ret = new StringBuilder();
        for (String str: arr) {
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
