package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.ExperienceService;
import ar.edu.itba.paw.service.MailingService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.service.EmployeeService;

import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ContactForm;
import ar.edu.itba.paw.webapp.form.EmployeeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class InitController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private MailingService mailingService;


    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("init");
        return mav;
    }

    @RequestMapping(value = "/redirectSearch", method = RequestMethod.GET)
    public ModelAndView redirectSearch() {
        return new ModelAndView("redirect:/buscarEmpleadas");
    }

    @RequestMapping(value = "/redirectCreateProfile", method = RequestMethod.GET)
    public ModelAndView redirectCreateProfile() {
        return new ModelAndView("redirect:/crearPerfil");
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

//    @ExceptionHandler(UserNotFoundException.class)
//    @ResponseStatus(code = HttpStatus.NOT_FOUND)
//    public ModelAndView noSuchUser() {
//        return new ModelAndView("404");
//    }

}
