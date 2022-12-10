package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.RaitingService;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/api/ratings")
@Component
public class RatingController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RaitingService ratingService;

    @GET
    @Path("/{employeeId}/{employerId}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getEmployeeRating(@PathParam("employeeId") long userId, @PathParam("employerId") long employerId) {
        try {
            Rating rating = new Rating(employeeService.getRating(userId), employeeService.getRatingVoteCount(userId), ratingService.hasAlreadyRated(userId, employerId));
            GenericEntity<Rating> genericEntity = new GenericEntity<Rating>(rating) {};
            return Response.ok(genericEntity).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    @POST
    @Path("")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response postRating(@FormDataParam("rating") Long rating, @FormDataParam("employeeId") long employeeId, @FormDataParam("employerId") long employerId) {
        if (rating == null)
            rating = 0L;
        if(ratingService.hasAlreadyRated(employeeId, employerId))
            return Response.serverError().build();
        float newRating = ratingService.updateRating(employeeId, rating, employerId);
        Rating r = new Rating(newRating, employeeService.getRatingVoteCount(employeeId), ratingService.hasAlreadyRated(employeeId, employerId));
        GenericEntity<Rating> genericEntity = new GenericEntity<Rating>(r) {};
        return Response.status(201).entity(genericEntity).build();
    }

    static class Rating {
        float rating;
        long count;

        boolean hasRated;


        public Rating(float rating, long count, boolean hasRated) {
            this.rating = rating;
            this.count = count;
            this.hasRated = hasRated;
        }

        public Rating() {
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public boolean isHasRated() {
            return hasRated;
        }

        public void setHasRated(boolean hasRated) {
            this.hasRated = hasRated;
        }

        @Override
        public String toString() {
            return "Rating{" +
                    "rating=" + rating +
                    ", count=" + count +
                    '}';
        }
    }
}
