package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.RaitingService;
import ar.edu.itba.paw.webapp.dto.RatingDto.RatingCretaeDto;
import ar.edu.itba.paw.webapp.dto.RatingDto.RatingDto;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Objects;

@Path("/api/ratings")
@Component
public class RatingController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RaitingService ratingService;

    @Context
    UriInfo uriInfo;

    @GET
    @Path("/{employeeId}/{employerId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getEmployeeRating(@PathParam("employeeId") long userId, @PathParam("employerId") long employerId) {
        try {
            RatingDto rating = RatingDto.fromRating(ratingService.getRating(userId), employeeService.getRatingVoteCount(userId), ratingService.hasAlreadyRated(userId, employerId));
            GenericEntity<RatingDto> genericEntity = new GenericEntity<RatingDto>(rating) {
            };
            return Response.ok(genericEntity).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response postRating(@Valid RatingCretaeDto ratingDto) {

        if (ratingService.hasAlreadyRated(ratingDto.getEmployeeId(), ratingDto.getEmployerId()))
            return Response.status(Response.Status.CONFLICT).build();

        float newRating = ratingService.updateRating(ratingDto.getEmployeeId(), ratingDto.getRating(), ratingDto.getEmployerId());
        //TODO: hay que cambiarlo, debería devlver la url para ir a buscar esta info (como esta abajo)
        //return Response.created(uriInfo.getBaseUriBuilder().path("/api/ratings/" + ratingDto.getEmployeeId() + "/" + ratingDto.getEmployerId()).build()).build();
        RatingDto r = RatingDto.fromRating(newRating, employeeService.getRatingVoteCount(ratingDto.getEmployeeId()), ratingService.hasAlreadyRated(ratingDto.getEmployeeId(), ratingDto.getEmployerId()));
        GenericEntity<RatingDto> genericEntity = new GenericEntity<RatingDto>(r) {
        };
        return Response.status(Response.Status.CREATED).entity(genericEntity).build();
    }
}
