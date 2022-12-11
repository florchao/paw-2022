package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/reviews")
@Component
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Context
    UriInfo uriInfo;

    private final static int PAGE_SIZE = 9;

    //TODO: los ids se pasan por body
    @POST
    @Path("")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response postJobReview(@FormDataParam("content") String message,
                                  @FormDataParam("employeeId") long employeeId,
                                  @FormDataParam("employerId") long employerId,
                                  @FormDataParam("forEmployee") boolean forEmployee) {
        //TODO: poner el id del empleado que esta iniciado sesión
        Review review = reviewService.create(employeeId, employerId, message, new Date(), forEmployee);
        ReviewDto reviewDto = ReviewDto.fromEmployeeReview(uriInfo, review);
        GenericEntity<ReviewDto> genericEntity = new GenericEntity<ReviewDto>(reviewDto) {
        };

        return Response.ok(genericEntity).build();
    }

}
