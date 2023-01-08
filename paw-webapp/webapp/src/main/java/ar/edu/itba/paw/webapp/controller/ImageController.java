package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.ImagesService;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.util.Optional;

@Path("/images")
@Component
public class ImageController {

    @Autowired
    private ImagesService imagesService;

    @GET
    @Path("/{id}")
    @Produces(value = {"image/webp",})
    public Response profileImage(@PathParam("id") long id, @Context Request request) {
        EntityTag etag = new EntityTag(String.valueOf(id));
        Response.ResponseBuilder response = getConditionalCacheResponse(request, etag);

        if (response == null) {
            Optional<byte[]> image = imagesService.getProfileImage(id);
            if (image.isPresent()) {
                return Response.ok(image.get()).tag(etag).build();
            }
            return Response.noContent().tag(etag).build();
        }
        return response.build();
    }

    @POST
    @Path("")
    @Produces(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response postImage(@FormDataParam("image") byte[] image, @FormDataParam("id") long id) {
        imagesService.insertImage(id, image);
        return Response.status(Response.Status.CREATED).build();
    }

    public static Response.ResponseBuilder getConditionalCacheResponse(Request request, EntityTag eTag) {
        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);
        if (response != null) {
            final CacheControl cacheControl = new CacheControl();
            cacheControl.setNoCache(true);
        }
        return response;
    }
}
