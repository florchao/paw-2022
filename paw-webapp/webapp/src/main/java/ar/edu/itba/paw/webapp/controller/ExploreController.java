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

    @RequestMapping("/buscarEmpleadas")
    public ModelAndView searchPage(@ModelAttribute("filterBy") FilterForm employeeForm, @RequestParam(value = "filterBoolean", required = false) Boolean filter) {
        List<Employee> list = new ArrayList<>();
        if (filter != null) {
            System.out.println("-----------");
            System.out.println(employeeForm.getName());
            System.out.println("-----------");
            for (Employee employee : employeeService.getFilteredEmployees(
                    employeeForm.getName(),
                    employeeForm.getExperienceYears(),
                    employeeForm.getLocation(),
                    employeeForm.getExperiencesList(),
                    employeeForm.getAvailability(),
                    employeeForm.getAbilities()
            ).get()) {
                list.add(firstWordsToUpper(employee));
            }
        } else {
            for (Employee employee : employeeService.getEmployees().get()) {
                list.add(firstWordsToUpper(employee));
            }
        }
        System.out.println(employeeForm.getExperienceYears());

        final ModelAndView mav = new ModelAndView("searchPage");
        mav.addObject("EmployeeList", list);
        return mav;

    }

    Employee firstWordsToUpper(Employee employee) {
        StringBuilder finalName = new StringBuilder();
        for (String word : employee.getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        employee.setName(finalName.toString());
        return employee;

    }

    @RequestMapping(value = "/filterEmployees", method = {RequestMethod.GET})
    public ModelAndView filterEmployees(@Valid @ModelAttribute("filterBy") FilterForm form, final BindingResult errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            System.out.println("me meti en error");
            return searchPage(form, false);
        }
        redirectAttributes.addFlashAttribute("filterBy", form);
//        redirectAttributes.addAttribute("experienceYears", form.getExperienceYears());
//        redirectAttributes.addAttribute("location", form.getLocation());
//        redirectAttributes.addAttribute("availability", form.getAvailability());
//        redirectAttributes.addAttribute("abilities", form.getAbilities());
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
