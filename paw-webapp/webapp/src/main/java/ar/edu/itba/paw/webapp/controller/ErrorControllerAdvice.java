package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handlingUserNotFound(){
        return new ModelAndView("404");
    }
    @ExceptionHandler(JobNotFoundException.class)
    public ModelAndView handlingJobNotFound(){return new ModelAndView("404");}
}
