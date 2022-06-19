package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Abilities;
import ar.edu.itba.paw.model.Availability;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.FilterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Controller
public class ExploreController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ContactService contactService;
    private final static long PAGE_SIZE = 9;

    @RequestMapping(value = "/buscarEmpleadas", method = {RequestMethod.GET})
    public ModelAndView searchPage(
            @ModelAttribute("filterBy") FilterForm employeeForm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "experienceYears", required = false) Long experienceYears,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "availability", required = false) String availability,
            @RequestParam(value = "abilities", required = false) String abilities,
            @RequestParam(value = "page", required = false) Long page,
            @RequestParam(value = "orderCriteria", required = false) String orderCriteria) {

        Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Authentication authority = SecurityContextHolder.getContext().getAuthentication();

        boolean anonymousSession = auth.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));

        if (page == null)
            page = 0L;
        Map<Employee, Boolean> list = new LinkedHashMap<>();
        List<Employee> employees = employeeService.getFilteredEmployees(name, experienceYears, location, availability, abilities,page, PAGE_SIZE, orderCriteria);
        for (Employee employee : employees) {
            employee.firstWordsToUpper();
            employee.locationFirstWordsToUpper();
            list.put(employee, false);
            if (!anonymousSession) {
                HogarUser user = (HogarUser) authority.getPrincipal();
                Boolean connection = contactService.existsContact(employee.getId().getId(), user.getUserID());
                if(connection){
                    list.put(employee, true);
                }
            }
        }
        final ModelAndView mav = new ModelAndView("searchPage");
        mav.addObject("EmployeeList", list);
        mav.addObject("page", page);
        mav.addObject("maxPage", employeeService.getPageNumber(name, experienceYears, location, availability, abilities, PAGE_SIZE));
        mav.addObject("abilities", Abilities.getIds());
        mav.addObject("availability", Availability.getIds());
        return mav;
    }

    @RequestMapping(value = "/filterEmployees", method = {RequestMethod.GET})
    public ModelAndView filterEmployees(@Valid @ModelAttribute("filterBy") FilterForm form, final BindingResult errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return searchPage(null, null,null,null,null,null,null, null);
        }
        if (!Objects.equals(form.getName(),"") && !form.getName().contains("\'") && !form.getName().contains("\"") )
            redirectAttributes.addAttribute("name",form.getName());
        if (form.getExperienceYears() > 0)
            redirectAttributes.addAttribute("experienceYears", form.getExperienceYears());
        if (!Objects.equals(form.getLocation(), ""))
            redirectAttributes.addAttribute("location", form.getLocation());
        if (form.getAvailability() != null && form.getAvailability().length > 0)
            redirectAttributes.addAttribute("availability", form.getAvailability());
        if (form.getAbilities() != null && form.getAbilities().length > 0)
            redirectAttributes.addAttribute("abilities", form.getAbilities());
        if (form.getPageNumber() > 0)
            redirectAttributes.addAttribute("page", form.getPageNumber());
        if (form.getOrderCriteria() != null && !form.getOrderCriteria().equals("")) {
            redirectAttributes.addAttribute("orderCriteria", form.getOrderCriteria());
        }
        System.out.println("mi ordercriteria es: "+form.getOrderCriteria());

        return new ModelAndView("redirect:/buscarEmpleadas");
    }
}
