package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

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
        GenericEntity<UserDto> genericEntity = new GenericEntity<UserDto>(userDto) {
        };
        return Response.ok(genericEntity).build();
    }

    @DELETE
    @Path("/users/{id}")
    public Response deleteUser(@PathParam("id") long id) {
//        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (hogarUser.getUserID() != id) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }
        userService.deleteUser(id);
        return Response.noContent().build();
    }
}
