package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/buscarEmpleadas")
    public ModelAndView searchPage(@ModelAttribute("filterBy") FilterForm employeeForm, @RequestParam(value = "filterBoolean", required = false) Boolean filter) {
        List<Employee> list;
        Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if(auth.contains(new SimpleGrantedAuthority("EMPLOYEE")))
            return new ModelAndView("redirect:/contactos");
//
        if (filter != null) {
            System.out.println("me meti!");
            list = employeeService.getFilteredEmployees(
                    employeeForm.getExperienceYears(),
                    employeeForm.getLocation(),
                    employeeForm.getExperiencesList(),
                    employeeForm.getAvailability(),
                    employeeForm.getAbilities()
            ).get();
        } else {
            list = employeeService.getEmployees().get();
        }
        System.out.println(employeeForm.getExperienceYears());

        final ModelAndView mav = new ModelAndView("searchPage");
        mav.addObject("EmployeeList", list);
        return mav;

    }

    @RequestMapping(value = "/filterEmployees", method = {RequestMethod.POST})
    public ModelAndView filterEmployees(@Valid @ModelAttribute("filterBy") FilterForm form, final BindingResult errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            System.out.println("me meti en error");
            return searchPage(form, false);
        }
        System.out.println("filtrar!");
        redirectAttributes.addFlashAttribute("filterBy", form);
        redirectAttributes.addAttribute("experienceYears", form.getExperienceYears());
        redirectAttributes.addAttribute("location", form.getLocation());
        redirectAttributes.addAttribute("availability", form.getAvailability());
        redirectAttributes.addAttribute("abilities", form.getAbilities());
        redirectAttributes.addAttribute("filterBoolean", true);
        return new ModelAndView("redirect:/buscarEmpleadas");
    }

    @ModelAttribute("orderBy")
    public Map<String, String> getOrderList() {
        Map<String, String> orderList = new HashMap<>();
        orderList.put("Rel","Relevancia");
        orderList.put("Ed", "Edad");
        return orderList;
    }
}
