package ar.edu.itba.paw.webapp.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class InitController {

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView helloWorld() {
        Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if(auth.contains(new SimpleGrantedAuthority("EMPLOYEE")))
            return new ModelAndView("redirect:/trabajos");
        if(auth.contains(new SimpleGrantedAuthority("EMPLOYER")))
            return new ModelAndView("redirect:/inicio");
        return new ModelAndView("init");
    }

    @RequestMapping(value = "/inicio", method = {RequestMethod.GET})
    public ModelAndView employerInit() {
        return new ModelAndView("employerLanding");
    }


    @RequestMapping(value = "/afterLogin", method = {RequestMethod.GET})
    public ModelAndView afterLogin() {
        Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if(auth.contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
            return new ModelAndView("redirect:/trabajos");
        }
        else {
            return new ModelAndView("redirect:/inicio");
        }
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("login");
    }

}
