package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/review")
@Component
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Context
    UriInfo uriInfo;

    private final static int PAGE_SIZE = 9;

    @GET
    @Path("/employee/{userId}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getEmployeeReviews(@PathParam("userId") long userId) {
        List<ReviewDto> reviews = reviewService.getAllReviews(userId, null, 0L, PAGE_SIZE).stream().map(r -> ReviewDto.fromEmployeeReview(uriInfo, r)).collect(Collectors.toList());

        //TODO: si es empleado el que inició sesión
        GenericEntity<List<ReviewDto>> genericEntity = new GenericEntity<List<ReviewDto>>(reviews){};
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/employer/{userId}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getEmployerReviews(@PathParam("userId") long userId) {
        List<ReviewDto> reviews = reviewService.getMyProfileReviewsEmployer(userId, 0L, PAGE_SIZE).stream().map(r -> ReviewDto.fromEmployerReview(uriInfo, r)).collect(Collectors.toList());
        GenericEntity<List<ReviewDto>> genericEntity = new GenericEntity<List<ReviewDto>>(reviews){};
        return Response.ok(genericEntity).build();
    }
}
