package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.ExperienceService;
import ar.edu.itba.paw.service.MailingService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.service.EmployeeService;

import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
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

    @RequestMapping("/buscarEmpleadas")
    public ModelAndView searchPage() {
        System.out.println(employeeService.getEmployees().get());
        System.out.println(experienceService.getAllExperiences().get());
        final ModelAndView mav = new ModelAndView("searchPage");
        return mav;
    }

    @RequestMapping("/crearPerfil")
    public ModelAndView createProfile() {
        final ModelAndView mav = new ModelAndView("createProfile");
        return mav;
    }
    @RequestMapping("/verPerfil")
    public ModelAndView viewProfile() {
        final ModelAndView mav = new ModelAndView("viewProfile");
        return mav;
    }

//    @ExceptionHandler(UserNotFoundException.class)
//    @ResponseStatus(code = HttpStatus.NOT_FOUND)
//    public ModelAndView noSuchUser() {
//        return new ModelAndView("404");
//    }

    @RequestMapping("/profile/{userId}")
    public ModelAndView userProfile(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("user", userService.getUserById(userId).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping("/chau")
    public ModelAndView goodbyeWorld() {
        final ModelAndView mav = new ModelAndView("byebye");
        mailingService.sendMail();
//        mav.addObject("user", userService.getUserById(1).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping(value = "/contactEmployee", method = RequestMethod.GET)
    public ModelAndView contactEmployee() {
        return new ModelAndView("redirect:/chau");
    }

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam(value = "name", required = true) final String username, @RequestParam(value = "password", required = true) final String password){
        final User u = userService.create(username, password);
        return new ModelAndView("redirect:/?userId=" + u.getId());

    }
}
