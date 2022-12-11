package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.NewPasswordForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/api")
@Component
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GET
    @Path("/user")
    public Response getUserInfo() {
        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userRole = hogarUser.getAuthorities().stream().findFirst().get().getAuthority();
        UserDto userDto = UserDto.fromForm(userRole, String.valueOf(hogarUser.getUserID()));
        GenericEntity<UserDto> genericEntity = new GenericEntity<UserDto>(userDto){};
        return Response.ok(genericEntity).build();
    }

    @PUT
    @Path("/users")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response updatePassword(@Valid final NewPasswordForm form) {
        boolean ans = userService.update(form.getMail(), form.getPassword());
        if(ans) {
            LOGGER.debug("password updated");
            return Response.ok().build();
        }
        else {
            return Response.notModified().build();
        }
    }

    @DELETE
    @Path("/users/{id}")
    public Response deleteUser(@PathParam("id") long id){
        userService.deleteUser(id);
        return Response.ok().build();
    }
}
