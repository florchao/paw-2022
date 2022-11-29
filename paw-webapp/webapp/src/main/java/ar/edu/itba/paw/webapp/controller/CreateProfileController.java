package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.EmployerService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.EmployeeForm;
import ar.edu.itba.paw.webapp.form.EmployerForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Arrays;

@Path("/api/create")
@Component
public class CreateProfileController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProfileController.class);

    @POST
    @Path("/employee")
    @Consumes(value = {MediaType.APPLICATION_JSON, })
    public Response createEmployee(@Valid final EmployeeForm form) {
//        User u = userService.create(form.getMail(), form.getPassword(), form.getConfirmPassword(), 1);
//        employeeService.create(form.getName().toLowerCase(), form.getLocation().toLowerCase(), u.getId(), form.fromArrtoString(form.getAvailability()), form.getExperienceYears(), form.fromArrtoString(form.getAbilities()), form.getImage().getBytes());
//        return Response.ok(u.getId()).build();

        System.out.println(form.getMail());
        System.out.println(form.getPassword());
        System.out.println(form.getConfirmPassword());
        System.out.println(form.getName());
        System.out.println(form.getLocation());
        System.out.println(Arrays.toString(form.getAbilities()));
        System.out.println(Arrays.toString(form.getAvailability()));
        System.out.println(form.getExperienceYears());
        System.out.println(form.getImage());
        return Response.ok(1).build();
    }

    @GET
    @Path("/hello")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response helloWorld() {
        return Response.ok("Hello world!").build();
    }



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
    @Consumes(value = {MediaType.APPLICATION_JSON, })
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
