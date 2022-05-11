package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping(value = "400", method = {RequestMethod.GET})
    public ModelAndView resourceNotFound() {
        return new ModelAndView("400");
    }

    @RequestMapping(value = "404", method = {RequestMethod.GET})
    public ModelAndView noSuchPath() {
        return new ModelAndView("404");
    }

    @RequestMapping(value = "403", method = {RequestMethod.GET})
    public ModelAndView accessDenied() {
        return new ModelAndView("403");
    }

    @RequestMapping(value = "500", method = {RequestMethod.GET})
    public ModelAndView serverError() {
        return new ModelAndView("500");
    }
}