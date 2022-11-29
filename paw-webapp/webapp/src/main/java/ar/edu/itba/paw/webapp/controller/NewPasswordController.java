package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.NewPasswordForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("/api/newPassword")
@Component
public class NewPasswordController {
    @Autowired
    private UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(NewPasswordController.class);

    @PUT
    @Path("/")
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
}
