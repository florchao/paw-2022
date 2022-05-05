package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.ExperienceService;
import ar.edu.itba.paw.service.MailingService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.FilterForm;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private MailingService mailingService;

    public String order;

    private final static long PAGE_SIZE = 4;

    @RequestMapping("/buscarEmpleadas")
    public ModelAndView searchPage(
            @ModelAttribute("filterBy") FilterForm employeeForm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "experienceYears", required = false) Long experienceYears,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "availability", required = false) String availability,
            @RequestParam(value = "abilities", required = false) String abilities,
            @RequestParam(value = "page", required = false) Long page) {
        if (page == null)
            page = 0L;
        List<Employee> list = new ArrayList<>();
        List<Experience> experiencesList = null;
        for (Employee employee : employeeService.getFilteredEmployees(name, experienceYears, location, experiencesList, availability, abilities,page,PAGE_SIZE).get()) {
            employee.firstWordsToUpper();
            list.add(employee);
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
        if (!Objects.equals(form.getName(),""))
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
