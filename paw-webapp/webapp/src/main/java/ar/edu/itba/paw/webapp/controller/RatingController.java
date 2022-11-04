package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.RaitingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/api/rating")
@Component
public class RatingController {

    @Autowired
    private EmployeeService employeeService;

    @GET
    @Path("/{userId}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getEmployeeRating(@PathParam("userId") long userId) {
        List<Number> info = new ArrayList<>();
        info.add(employeeService.getRating(userId));
        info.add(employeeService.getRatingVoteCount(userId));
        GenericEntity<List<Number>> genericEntity = new GenericEntity<List<Number>>(info){};
        return Response.ok(genericEntity).build();
    }
}
