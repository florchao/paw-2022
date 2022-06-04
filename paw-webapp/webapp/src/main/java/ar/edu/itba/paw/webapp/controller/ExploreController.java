package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;
import ar.edu.itba.paw.model.exception.AccessIsDeniedException;
import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.FilterForm;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final static long PAGE_SIZE = 4;

    @RequestMapping(value = "/buscarEmpleadas", method = {RequestMethod.GET})
    public ModelAndView searchPage(
            @ModelAttribute("filterBy") FilterForm employeeForm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "experienceYears", required = false) Long experienceYears,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "availability", required = false) String availability,
            @RequestParam(value = "abilities", required = false) String abilities,
            @RequestParam(value = "page", required = false) Long page) {

        Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if(auth.contains(new SimpleGrantedAuthority("EMPLOYEE")))
            throw new AccessIsDeniedException("Acces is denied");
        Authentication authority = SecurityContextHolder.getContext().getAuthentication();
        HogarUser user = (HogarUser) authority.getPrincipal();
        if (page == null)
            page = 0L;
        Map<Employee, Boolean> list = new HashMap<>();
        List<Experience> experiencesList = null;
        for (Employee employee : employeeService.getFilteredEmployees(name, experienceYears, location, experiencesList, availability, abilities,page,PAGE_SIZE).get()) {
            employee.firstWordsToUpper();
            Optional<Boolean> connection = contactService.existsContact(employee.getId().getId(), user.getUserID());
            if(connection.isPresent() && connection.get()){
                list.put(employee, true);
            }
            else{
                list.put(employee, false);
            }
        }
        final ModelAndView mav = new ModelAndView("searchPage");
        mav.addObject("EmployeeList", list);
        mav.addObject("page", page);
        mav.addObject("maxPage", employeeService.getPageNumber(name, experienceYears, location, experiencesList, availability, abilities, PAGE_SIZE));
        return mav;
    }

    @RequestMapping(value = "/filterEmployees", method = {RequestMethod.GET})
    public ModelAndView filterEmployees(@Valid @ModelAttribute("filterBy") FilterForm form, final BindingResult errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return searchPage(null, null,null,null,null,null,null);
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

        return new ModelAndView("redirect:/buscarEmpleadas");
    }
}
