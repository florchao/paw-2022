package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;

import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    private UserService us;

    @Autowired
    public HelloWorldController(final UserService us) {
        this.us = us;
    }

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
        mav.addObject("user", us.getUserById(userId).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping("/chau")
    public ModelAndView goodbyeWorld() {
        final ModelAndView mav = new ModelAndView("byebye");
        mav.addObject("user", us.getUserById(1).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam(value = "name", required = true) final String username, @RequestParam(value = "password", required = true) final String password){
        final User u = us.create(username, password);
        return new ModelAndView("redirect:/?userId=" + u.getId());

    }
}
