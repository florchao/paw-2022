package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.ImagesService;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayInputStream;
import java.util.Optional;

@Path("/api/images")
@Component
public class ImageController {

    @Autowired
    private ImagesService imagesService;

//    @Autowired
//    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = {"image/webp",})
    public Response profileImage(@PathParam("id") long id) {

        Optional<byte[]> image = imagesService.getProfileImage(id);
        if (image.isPresent()) {
            Response.ResponseBuilder response = Response.ok(image.get());
            return response.build();
        }
        return Response.noContent().build();
    }

    @POST
    @Path("")
    @Produces(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response postImage(@FormDataParam("image") byte[] image, @FormDataParam("id") long id) {
        imagesService.insertImage(id, image);
        return Response.status(Response.Status.CREATED).build();
    }
}
