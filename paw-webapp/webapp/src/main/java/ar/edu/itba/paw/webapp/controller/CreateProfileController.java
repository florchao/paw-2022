package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.EmployerService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.EmployeeForm;
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
import java.util.Arrays;
import java.util.List;

@Path("/api")
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

    @GET
    @Path("/hello")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response helloWorld() {
        return Response.ok("Hello world!").build();
    }

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

    public String fromListToString(List<String> arr){
        StringBuilder ret = new StringBuilder();
        for (String str: arr) {
            ret.append(str).append(",");
        }
        return ret.substring(0, ret.length() - 1);
    }

}
