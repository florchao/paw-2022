package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.EmployerService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.EmployerDto;
import ar.edu.itba.paw.webapp.form.EmployerForm;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Path("/api/employer")
@Component
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerController.class);


    @GET
    @Path(value = "/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response employerProfile(@PathParam("id") long id) throws UserNotFoundException {
        //        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails principal = (UserDetails) auth.getPrincipal();
//        if (page == null)
//            page = 0L;
//        Optional<User> user = userService.findByUsername(principal.getUsername());
//        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
//            if (user.isPresent()) {
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
        return Response.serverError().build();
    }

    @POST
    @Path("/")
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
//        HogarUser current = new HogarUser(form.getMail(), u.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(String.valueOf((u.getRole())))), name, u.getId());
//        Authentication auth = new UsernamePasswordAuthenticationToken(current,null, Collections.singletonList(new SimpleGrantedAuthority("EMPLOYER")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
        employerService.create(fullName.toLowerCase(), u, IOUtils.toByteArray(image));
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        principal.setName(name);
        LOGGER.debug(String.format("employer created under userid %d", u.getId()));
        return Response.ok(u.getId()).build();
    }
}
