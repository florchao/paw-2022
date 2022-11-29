package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Abilities;
import ar.edu.itba.paw.model.Availability;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.EmployerService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.EmployeeForm;
import ar.edu.itba.paw.webapp.form.EmployerForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Controller@Path("/api/register")
@Component
public class CreateProfileController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProfileController.class);

//    @RequestMapping(value = "/registrarse", method = {RequestMethod.GET})
//    public ModelAndView register(){
//        return new ModelAndView("register");
//    }
//    @RequestMapping(value = "/crearPerfil", method = {RequestMethod.GET})
//    public ModelAndView createProfile(@ModelAttribute("employeeForm") final EmployeeForm form) {
//        ModelAndView mav = new ModelAndView("createProfile");
//        mav.addObject("abilities", Abilities.getIds());
//        mav.addObject("availability", Availability.getIds());
//        return mav;
//    }

//    @RequestMapping(value = "/createEmployee", method = {RequestMethod.POST})
//    public ModelAndView create(@Valid @ModelAttribute("employeeForm") final EmployeeForm form, final BindingResult errors) throws UserFoundException, PassMatchException {
//        if(errors.hasErrors()) {
//            LOGGER.debug("couldn't create employee profile");
//            return createProfile(form);
//        }
//        final User u = userService.create(form.getMail(), form.getPassword(), form.getConfirmPassword(), 1);
//        HogarUser current = new HogarUser(form.getMail(), u.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(String.valueOf((u.getRole())))), form.getName(), u.getId());
//        Authentication auth = new UsernamePasswordAuthenticationToken(current,null, Collections.singletonList(new SimpleGrantedAuthority("EMPLOYEE")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        employeeService.create(form.getName().toLowerCase(), form.getLocation().toLowerCase(), u.getId(), form.fromArrtoString(form.getAvailability()), form.getExperienceYears(), form.fromArrtoString(form.getAbilities()), form.getImage().getBytes());
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        principal.setName(form.getName());
//        LOGGER.debug(String.format("employee created under userid %d", principal.getUserID()));
//        return new ModelAndView("redirect:/verPerfil");
//    }
//
//    @RequestMapping(value = "/crearPerfilEmpleador", method = {RequestMethod.GET})
//    public ModelAndView createProfileEmployer(@ModelAttribute("employerForm") final EmployerForm form) {
//        return new ModelAndView("createProfileEmployer");
//    }

    @POST
    @Path("/employer")
    @RequestMapping(value = {MediaType.APPLICATION_JSON})
    public Response createEmployer(@Valid EmployerForm form) throws UserFoundException, PassMatchException {
        final User u = userService.create(form.getMail(), form.getPassword(), form.getConfirmPassword(), 2);
        String name = form.getName() + " " + form.getLastname();
//        HogarUser current = new HogarUser(form.getMail(), u.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(String.valueOf((u.getRole())))), name, u.getId());
//        Authentication auth = new UsernamePasswordAuthenticationToken(current,null, Collections.singletonList(new SimpleGrantedAuthority("EMPLOYER")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
        employerService.create(name.toLowerCase(), u, form.getImage().getBytes());
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        principal.setName(name);
//        LOGGER.debug(String.format("employer created under userid %d", principal.getUserID()));
        return Response.ok().build();
    }

}
