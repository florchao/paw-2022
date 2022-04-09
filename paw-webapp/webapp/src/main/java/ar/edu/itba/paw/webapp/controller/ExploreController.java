package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.ExperienceService;
import ar.edu.itba.paw.service.MailingService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/buscarEmpleadas")
    public ModelAndView searchPage() {
        System.out.println(employeeService.getEmployees().get());
        System.out.println(experienceService.getAllExperiences().get());
        final ModelAndView mav = new ModelAndView("searchPage");
        return mav;
    }
}