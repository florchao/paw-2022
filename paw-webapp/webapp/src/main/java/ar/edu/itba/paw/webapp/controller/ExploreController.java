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

    private final static long PAGE_SIZE = 2;

    @RequestMapping("/buscarEmpleadas")
    public ModelAndView searchPage(
            @ModelAttribute("filterBy") FilterForm employeeForm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "experienceYears", required = false) Long experienceYears,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "availability", required = false) String availability,
            @RequestParam(value = "abilities", required = false) String abilities,
            @RequestParam(value = "page", required = false) Long page) {
        System.out.println("pagina: "+page);
        if (page == null)
            page = 0L;
        System.out.println("pagina: "+page);
        List<Employee> list = new ArrayList<>();
        System.out.println("-----------");
        System.out.println(name);
        System.out.println(experienceYears);
        System.out.println(location);
        System.out.println(availability);
        System.out.println(abilities);
        System.out.println("-----------");
        List<Experience> experiencesList = null;
        for (Employee employee : employeeService.getFilteredEmployees(name, experienceYears, location, experiencesList, availability, abilities,page,PAGE_SIZE).get()) {
            list.add(firstWordsToUpper(employee));
        }

        final ModelAndView mav = new ModelAndView("searchPage");
        mav.addObject("EmployeeList", list);
        mav.addObject("page", page);
        mav.addObject("maxPage", employeeService.getPageNumber(name, experienceYears, location, experiencesList, availability, abilities, PAGE_SIZE));
//        System.out.println("el valor de max number en controller vale " + employeeService.getPageNumber(name, experienceYears, location, experiencesList, availability, abilities, PAGE_SIZE));
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
            return searchPage(null, null,null,null,null,null, null);
        }
        redirectAttributes.addAttribute("name",form.getName());
        if (form.getExperienceYears() > 0)
            redirectAttributes.addAttribute("experienceYears", form.getExperienceYears());
        if (!Objects.equals(form.getLocation(), ""))
            redirectAttributes.addAttribute("location", form.getLocation());
        System.out.println("availability: ");
        if (form.getAvailability().length > 0)
            redirectAttributes.addAttribute("availability", form.getAvailability());
        if (form.getAbilities().length > 0)
            redirectAttributes.addAttribute("abilities", form.getAbilities());

        System.out.println(form.getAvailability().length);
        System.out.println("abilities: ");
        System.out.println(form.getAbilities().length);
        System.out.println("valor de pagina en filtrado es: "+form.getPageNumber());
        if (form.getPageNumber() > 0)
            redirectAttributes.addAttribute("page", form.getPageNumber());

        return new ModelAndView("redirect:/buscarEmpleadas");
    }

    @RequestMapping(value = "/filterPage", method = {RequestMethod.GET})
    public ModelAndView pageFilter(@RequestParam("id2") String id2) {
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
