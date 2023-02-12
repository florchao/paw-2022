package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.ImagesService;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Path("/images")
@Component
public class ImageController {

    @Autowired
    private ImagesService imagesService;

    @Context
    private UriInfo uriInfo;

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
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response postImage(@FormDataParam("image") byte[] image, @FormDataParam("id") long id) {
        imagesService.insertImage(id, image);
        return Response.created(uriInfo.getBaseUriBuilder().path("/images").path(String.valueOf(id)).build()).header("Access-Control-Expose-Headers", "Location").build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response putImage(@PathParam("id") long id, @FormDataParam("image") InputStream image) throws IOException {
        boolean updated = imagesService.updateProfileImage(id, IOUtils.toByteArray(image));
        if (updated)
            return Response.status(Response.Status.OK).entity(uriInfo.getBaseUriBuilder().path("/images").path(String.valueOf(id)).build()).build();
        else
            return Response.status(Response.Status.CONFLICT).build();
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
