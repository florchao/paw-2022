package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.Objects;

@Path("/api/reviews")
@Component
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Context
    UriInfo uriInfo;


    @POST
    @Path("")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response postJobReview(@FormDataParam("content") String message,
                                  @FormDataParam("employeeId") Long employeeId,
                                  @FormDataParam("employerId") Long employerId,
                                  @FormDataParam("forEmployee") Boolean forEmployee) {
        if (message.length() < 10 || message.length() > 100 ||
                Objects.equals(employeeId, employerId) || Objects.isNull(employeeId) || Objects.isNull(employerId)
                || Objects.isNull(forEmployee)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Review review = reviewService.create(employeeId, employerId, message, new Date(), forEmployee);
        if (review == null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        ReviewDto reviewDto;
        if (forEmployee)
            reviewDto = ReviewDto.fromEmployeeReview(uriInfo, review);
        else
            reviewDto = ReviewDto.fromEmployerReview(uriInfo, review);
        GenericEntity<ReviewDto> genericEntity = new GenericEntity<ReviewDto>(reviewDto) {
        };

        return Response.status(Response.Status.CREATED).entity(genericEntity).build();
    }

}
