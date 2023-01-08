package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.model.Abilities;
import ar.edu.itba.paw.model.Availability;
import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.webapp.dto.IdsDto;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ids")
@Component
public class IdsController {

    @GET
    @Path("")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getIds() {
        GenericEntity<IdsDto> genericEntity = new GenericEntity<IdsDto>(IdsDto.fromForm(Availability.getIds(), Abilities.getIds(), Location.getIds())) {
        };
        return Response.ok(genericEntity).build();
    }

}
