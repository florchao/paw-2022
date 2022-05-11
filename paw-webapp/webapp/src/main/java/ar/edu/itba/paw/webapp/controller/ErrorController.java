package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping("400")
    public ModelAndView resourceNotFound() {
        return new ModelAndView("400");
    }

    @RequestMapping("404")
    public ModelAndView noSuchPath() {
        return new ModelAndView("404");
    }

    @RequestMapping("403")
    public ModelAndView accessDenied() {
        return new ModelAndView("403");
    }

    @RequestMapping("500")
    public ModelAndView serverError() {
        return new ModelAndView("500");
    }
}