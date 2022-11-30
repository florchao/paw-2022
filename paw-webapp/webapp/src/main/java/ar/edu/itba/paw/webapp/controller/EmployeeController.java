package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Abilities;
import ar.edu.itba.paw.model.Availability;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.EmployeeDto;
import ar.edu.itba.paw.webapp.form.EmployeeEditForm;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Optional;

@Path("/api/employee")
@Component
public class EmployeeController {
    private final int PAGE_SIZE = 4;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);


    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response employeeProfile(@PathParam("id") long id) throws UserNotFoundException {
//        final ModelAndView mav = new ModelAndView("viewProfile");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<EmployeeDto> employee = employeeService.getEmployeeById(id).map(e -> EmployeeDto.fromProfile(uriInfo, e));
        if (employee.isPresent()) {
            GenericEntity<EmployeeDto> genericEntity = new GenericEntity<EmployeeDto>(employee.get()){};
            return Response.ok(genericEntity).build();
        }
        return Response.serverError().build();
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

    @POST
    @Path("/")
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
        employeeService.create(name, location.toLowerCase(), u.getId(), fromListToString(availabilities), experienceYears, fromListToString(abilities), IOUtils.toByteArray(image));
        return Response.ok(u.getId()).build();
        //Probando que llegue bien la información
//        System.out.println(mail);
//        System.out.println(password);
//        System.out.println(confirmPassword);
//        System.out.println(name);
//        System.out.println(location);
//        System.out.println(availabilities);
//        System.out.println(abilities);
//        System.out.println(experienceYears);
//        return Response.ok(1).build();
    }

    //TODO: PUT y DELETE? de employee

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
}
