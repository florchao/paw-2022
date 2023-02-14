package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.webapp.dto.ReviewDto.ReviewCreateDto;
import ar.edu.itba.paw.webapp.dto.ReviewDto.EmployeeReviewDto;
import ar.edu.itba.paw.webapp.dto.ReviewDto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.Optional;

@Path("/reviews")
@Component
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Context
    UriInfo uriInfo;

    //forEmployee: 0 will get the review done by {employer} to {employee}
    //             1 will get the review done by {employee} to {employer}
    @GET
    @Path("/{employeeId}/{employerId}/{forEmployee}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getMyReviewToEmployee(@PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId, @PathParam("forEmployee") @Min(0) @Max(1) int forEmployee) {
        Optional<ReviewDto> myReview;
        if(forEmployee == 0)
            myReview = reviewService.getMyReview(employeeId, employerId).map(r -> ReviewDto.fromReview(uriInfo, r, true));
        else
            myReview = reviewService.getMyReviewEmployer(employeeId, employerId).map(r -> ReviewDto.fromReview(uriInfo, r, false));
        if (!myReview.isPresent())
            return Response.noContent().build();
        GenericEntity<ReviewDto> genericEntity = new GenericEntity<ReviewDto>(myReview.get()) {
        };
        return Response.ok(genericEntity).build();

    }

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

        return Response.
                created(location.build())
                .build();
    }

}
