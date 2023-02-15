package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.webapp.dto.IdsDto.AbilitiesDto;
import ar.edu.itba.paw.webapp.dto.IdsDto.AvailabilitiesDto;
import ar.edu.itba.paw.webapp.dto.IdsDto.LocationDto;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Locale;


@Component
@Path("")
public class IdsController {

    @GET
    @Path("/availabilities")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getAv(@Context HttpServletRequest request) {

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);
        GenericEntity<AvailabilitiesDto> genericEntity = new GenericEntity<AvailabilitiesDto>(AvailabilitiesDto.fromForm()) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/abilities")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getAb(@Context HttpServletRequest request) {

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);
        GenericEntity<AbilitiesDto> genericEntity = new GenericEntity<AbilitiesDto>(AbilitiesDto.fromForm()) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/locations")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getLoc(@Context HttpServletRequest request) {
        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);
        GenericEntity<LocationDto> genericEntity = new GenericEntity<LocationDto>(LocationDto.fromForm()) {
        };
        return Response.ok(genericEntity).build();
    }



}
