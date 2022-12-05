package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import ar.edu.itba.paw.webapp.form.ContactUsForm;
import ar.edu.itba.paw.webapp.form.JobForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getEmployeeReviews(@PathParam("userId") long userId) {
        List<ReviewDto> reviews = reviewService.getAllReviews(4L, userId, 0L, PAGE_SIZE).stream().map(r -> ReviewDto.fromEmployeeReview(uriInfo, r)).collect(Collectors.toList());

        //TODO: si es empleado el que inició sesión
        GenericEntity<List<ReviewDto>> genericEntity = new GenericEntity<List<ReviewDto>>(reviews) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/employer/{userId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getEmployerReviews(@PathParam("userId") long userId) {
        //todo employeeId hardcodeado

        List<ReviewDto> reviews = reviewService.getAllReviewsEmployer(4L, userId, 0L, PAGE_SIZE).stream().map(r -> ReviewDto.fromEmployerReview(uriInfo, r)).collect(Collectors.toList());
        GenericEntity<List<ReviewDto>> genericEntity = new GenericEntity<List<ReviewDto>>(reviews) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/employer/{employeeId}/{employerId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getMyReviewToEmployer(@PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId) {
        Optional<ReviewDto> myReview = reviewService.getMyReviewEmployer(employeeId, employerId).map(r -> ReviewDto.fromEmployerReview(uriInfo, r));
        if(!myReview.isPresent())
            return Response.noContent().build();
        GenericEntity<ReviewDto> genericEntity = new GenericEntity<ReviewDto>(myReview.get()){};
        return Response.ok(genericEntity).build();

    }

    @GET
    @Path("/employee/{employerId}/{employeeId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getMyReviewToEmployee(@PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId) {
        Optional<ReviewDto> myReview = reviewService.getMyReview(employeeId, employerId).map(r -> ReviewDto.fromEmployeeReview(uriInfo, r));
        if(!myReview.isPresent())
            return Response.noContent().build();
        GenericEntity<ReviewDto> genericEntity = new GenericEntity<ReviewDto>(myReview.get()){};
        return Response.ok(genericEntity).build();

    }

    @POST
    @Path("/employer/{employerId}/{employeeId}")
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA, })
    public Response postJobReview(@FormDataParam("content") String message, @PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId) {
        //TODO: poner el id del empleado que esta iniciado sesión
        ReviewDto review = ReviewDto.fromEmployeeReview(uriInfo ,reviewService.create(employeeId, employerId, message ,new Date(), false ));
        GenericEntity<ReviewDto> genericEntity = new GenericEntity<ReviewDto>(review){};

        return Response.ok(genericEntity).build();
    }

    @POST
    @Path("/employee/{employeeId}/{employerId}")
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA, })
    public Response postEmployeeReview(@FormDataParam("content") String message, @PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId) {
        //TODO: poner el id del empleador que esta iniciado sesión
        Review aux = reviewService.create(employeeId, employerId, message ,new Date(), true );
        System.out.println("FORMM " + aux.toString());
        ReviewDto review = ReviewDto.fromEmployerReview(uriInfo , aux);
        System.out.println("FORMM " + review.toString());
        GenericEntity<ReviewDto> genericEntity = new GenericEntity<ReviewDto>(review){};

        return Response.ok(genericEntity).build();
    }

}
