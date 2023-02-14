package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.RaitingService;
import ar.edu.itba.paw.webapp.dto.RatingDto.RatingCretaeDto;
import ar.edu.itba.paw.webapp.dto.RatingDto.RatingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/ratings")
@Component
public class RatingController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RaitingService ratingService;

    @Context
    UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);

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
            LOGGER.error("an exception occurred:", e);
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response postRating(@Valid RatingCretaeDto ratingDto) {

        if (ratingService.hasAlreadyRated(ratingDto.getEmployeeId(), ratingDto.getEmployerId()))
            return Response.status(Response.Status.CONFLICT).build();

        ratingService.updateRating(ratingDto.getEmployeeId(), ratingDto.getRating(), ratingDto.getEmployerId());

        return Response
                .created(uriInfo.getBaseUriBuilder()
                        .path("/ratings/" + ratingDto.getEmployeeId())
                        .build())
                .build();

    }
}
