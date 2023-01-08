package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.webapp.dto.ReviewDto.ReviewCreateDto;
import ar.edu.itba.paw.webapp.dto.ReviewDto.ReviewDto;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.Objects;

@Path("/reviews")
@Component
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Context
    UriInfo uriInfo;


    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response postJobReview(@Valid ReviewCreateDto reviewCreateDto) {
//        if (message.length() < 10 || message.length() > 100 ||
//                Objects.equals(employeeId, employerId) || Objects.isNull(employeeId) || Objects.isNull(employerId)
//                || Objects.isNull(forEmployee)) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
        Review review = reviewService.create(reviewCreateDto.getEmployeeId(), reviewCreateDto.getEmployerId(), reviewCreateDto.getContent(), new Date(), reviewCreateDto.getForEmployee());
        if (review == null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        ReviewDto reviewDto;
        if (reviewCreateDto.getForEmployee())
            reviewDto = ReviewDto.fromEmployeeReview(uriInfo, review);
        else
            reviewDto = ReviewDto.fromEmployerReview(uriInfo, review);
        GenericEntity<ReviewDto> genericEntity = new GenericEntity<ReviewDto>(reviewDto) {
        };

        //TODO: devolver la URI no el objeto
        return Response.status(Response.Status.CREATED).entity(genericEntity).build();
    }

}
