package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.webapp.dto.ReviewDto.ReviewCreateDto;
import ar.edu.itba.paw.webapp.dto.ReviewDto.EmployeeReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.util.Date;

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

        Review review = reviewService.create(reviewCreateDto.getEmployeeId(), reviewCreateDto.getEmployerId(), reviewCreateDto.getContent(), new Date(), reviewCreateDto.getForEmployee());
        if (review == null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        UriBuilder location = uriInfo.getBaseUriBuilder();
        if (reviewCreateDto.getForEmployee())
            location = location.path("/employees").path(String.valueOf(reviewCreateDto.getEmployeeId())).path("/reviews");
        else
            location = location.path("/employers").path(String.valueOf(reviewCreateDto.getEmployerId())).path("/reviews");

        return Response.created(location.build()).build();
    }

}
