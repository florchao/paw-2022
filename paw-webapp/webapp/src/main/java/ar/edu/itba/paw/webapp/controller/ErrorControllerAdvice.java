package ar.edu.itba.paw.webapp.controller;

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
}
