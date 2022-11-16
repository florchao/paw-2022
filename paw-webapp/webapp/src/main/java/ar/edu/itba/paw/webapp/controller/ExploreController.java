package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Abilities;
import ar.edu.itba.paw.model.Availability;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.dto.EmployeeDto;
import ar.edu.itba.paw.webapp.form.FilterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.stream.Collectors;

@Path("/api/explore")
@Component
public class ExploreController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ContactService contactService;

    @Context
    private UriInfo uriInfo;
    private final static long PAGE_SIZE = 9;

    @GET
    @Path("/employees")
    @Produces(value = { MediaType.APPLICATION_JSON })
    public Response filterEmployees(
            @QueryParam("name") String name,
            @QueryParam("experience") Long experienceYears,
            @QueryParam("location") String location,
            @QueryParam("availability") String availability,
            @QueryParam("abilities") String abilities,
            @QueryParam("page") Long page,
            @QueryParam("order") String orderCriteria
    ) {

        if (page == null)
            page = 0L;
        Map<EmployeeDto, Boolean> list = new LinkedHashMap<>();
        List<EmployeeDto> employees = employeeService.getFilteredEmployees(name, experienceYears, location, availability, abilities, page, PAGE_SIZE, orderCriteria).stream().map(e -> EmployeeDto.fromExplore(uriInfo, e)).collect(Collectors.toList());
//        List<Employee> employees = employeeService.getFilteredEmployees(null, null, null, null, null, 0L, PAGE_SIZE, null);
        for (EmployeeDto employee : employees) {
            list.put(employee, false);
        }
        GenericEntity<List<EmployeeDto>> genericEntity = new GenericEntity<List<EmployeeDto>>(employees){};
        return Response.ok(genericEntity).build();
    }

//    @RequestMapping(value = "/filterEmployees", method = {RequestMethod.GET})
//    public ModelAndView filterEmployees(@Valid @ModelAttribute("filterBy") FilterForm form, final BindingResult errors, RedirectAttributes redirectAttributes) throws UserNotFoundException {
//        if (errors.hasErrors()) {
//            return searchPage(null, null,null,null,null,null,null, null);
//        }
//        if (!Objects.equals(form.getName(),""))
//            redirectAttributes.addAttribute("name",form.getName());
//        if (form.getExperienceYears() > 0)
//            redirectAttributes.addAttribute("experienceYears", form.getExperienceYears());
//        if (!Objects.equals(form.getLocation(), ""))
//            redirectAttributes.addAttribute("location", form.getLocation());
//        if (form.getAvailability() != null && form.getAvailability().length > 0)
//            redirectAttributes.addAttribute("availability", form.getAvailability());
//        if (form.getAbilities() != null && form.getAbilities().length > 0)
//            redirectAttributes.addAttribute("abilities", form.getAbilities());
//        if (form.getPageNumber() > 0)
//            redirectAttributes.addAttribute("page", form.getPageNumber());
//        if (form.getOrderCriteria() != null && !form.getOrderCriteria().equals("")) {
//            redirectAttributes.addAttribute("orderCriteria", form.getOrderCriteria());
//        }
//        return new ModelAndView("redirect:/buscarEmpleadas");
//    }
}
