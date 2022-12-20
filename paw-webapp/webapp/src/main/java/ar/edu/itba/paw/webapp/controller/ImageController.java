package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.util.Optional;

@Path("/api/images")
@Component
public class ImageController {

    @Autowired
    private ImagesService imagesService;

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response profileImage(@PathParam("id") long id) {

        Optional<byte[]> image = imagesService.getProfileImage(id);
        if (image.isPresent()) {
            Response.ResponseBuilder response = Response.ok(new ByteArrayInputStream(image.get()));
            return response.build();
        }
        return Response.noContent().build();
    }
}
