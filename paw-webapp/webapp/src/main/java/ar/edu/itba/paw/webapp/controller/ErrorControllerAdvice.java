package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exception.AccessIsDeniedException;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView handlingUserNotFound(){
        LOGGER.warn("User could not be found");
        return new ModelAndView("404");
    }
    @ExceptionHandler(JobNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView handlingJobNotFound(){
        LOGGER.warn("Job could not be found");
        return new ModelAndView("404");}

    @ExceptionHandler(AccessIsDeniedException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ModelAndView handlingAccessIsDenied(){
        LOGGER.warn("Access is denied");
        return new ModelAndView("403");}
}
