package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.RaitingService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.EmployeeDto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Path("/employees")
@Component
public class EmployeesController {

    private final int PAGE_SIZE = 8;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ContactService contactService;
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response filterEmployees(
            @QueryParam("name") String name,
            @QueryParam("experience") Long experienceYears,
            @QueryParam("location") String location,
            @QueryParam("availability") String availability,
            @QueryParam("abilities") String abilities,
            @QueryParam("page") @DefaultValue("0") Long page,
            @QueryParam("order") String orderCriteria,
            @Context HttpServletRequest request
    ) {
        if (experienceYears != null && (experienceYears < 0 || experienceYears > 100)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (location != null && (!location.matches("[1-4][,[1-4]]*") || location.length() > 7)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (availability != null && (!availability.matches("[1-3][,[1-3]]*") || availability.length() > 5))
            return Response.status(Response.Status.BAD_REQUEST).build();
        if (abilities != null && (!abilities.matches("[1-6][,[1-6]]*") || abilities.length() > 11))
            return Response.status(Response.Status.BAD_REQUEST).build();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HogarUser hogarUser;

        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
            hogarUser = (HogarUser) auth.getPrincipal();
        } else {
            hogarUser = null;
        }

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 5));
        LocaleContextHolder.setLocale(locale);

        List<EmployeeDto> employees = employeeService.getFilteredEmployees(name, experienceYears, location, availability, abilities, page, PAGE_SIZE, orderCriteria).stream().map(employee ->
        {
            if (hogarUser != null) {
                Boolean hasContact = !contactService.existsContact(employee.getId().getId(), hogarUser.getUserID()).isEmpty();
                return EmployeeDto.fromProfile(uriInfo, employee, false, hasContact);
            } else
                return EmployeeDto.fromProfile(uriInfo, employee, true, false);
        }).collect(Collectors.toList());
        int pages = employeeService.getPageNumber(name, experienceYears, location, availability, abilities, PAGE_SIZE, orderCriteria);
        GenericEntity<List<EmployeeDto>> genericEntity = new GenericEntity<List<EmployeeDto>>(employees) {
        };
        return Response.ok(genericEntity).header("X-Total-Count", pages).build();
    }
}
