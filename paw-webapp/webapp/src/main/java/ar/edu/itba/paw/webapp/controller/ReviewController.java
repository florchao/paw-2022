package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/review")
@Component
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    private final static int PAGE_SIZE = 9;

    @GET
    @Path("/employee/{userId}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getEmployeeReviews(@PathParam("userId") long userId) {
        List<Review> reviews = reviewService.getAllReviews(userId, null, 0L, PAGE_SIZE);
        for (Review rev : reviews) {
            rev.getEmployerId().firstWordsToUpper();
        }
        //TODO: si es empleado el que inició sesión
        GenericEntity<List<Review>> genericEntity = new GenericEntity<List<Review>>(reviews){};
        return Response.ok(genericEntity).build();
    }
}
