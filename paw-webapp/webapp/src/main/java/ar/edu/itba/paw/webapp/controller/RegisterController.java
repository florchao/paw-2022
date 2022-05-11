package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @RequestMapping(value = "/registrarse", method = {RequestMethod.GET})
    public ModelAndView register(@ModelAttribute("registerForm") final RegisterForm form){
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public ModelAndView registerUser(@Valid @ModelAttribute("registerForm") final RegisterForm form, final BindingResult errors, HttpServletRequest request){

        ModelAndView mav = new ModelAndView("register");

        if (errors.hasErrors()){
            LOGGER.debug("Could not register profile");
            return register(form);
        }
        try{
            int role = form.getRole().equals("Empleada")? 1 : 2;
            final User u = userService.create(form.getMail(), form.getPassword(), form.getConfirmPassword(), role);
            if(role == 1) {
                HogarUser current = new HogarUser(form.getMail(), u.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(String.valueOf((u.getRole())))), null, u.getId());
                Authentication auth = new UsernamePasswordAuthenticationToken(current,null, Collections.singletonList(new SimpleGrantedAuthority("EMPLOYEE")));
                SecurityContextHolder.getContext().setAuthentication(auth);
                return new ModelAndView("redirect:/crearPerfil/" + u.getId());
            }
            else {
                HogarUser current = new HogarUser(form.getMail(), u.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(String.valueOf((u.getRole())))), null, u.getId());
                Authentication auth = new UsernamePasswordAuthenticationToken(current,null, Collections.singletonList(new SimpleGrantedAuthority("EMPLOYER")));
                SecurityContextHolder.getContext().setAuthentication(auth);
                return new ModelAndView("redirect:/crearPerfilEmpleador/" + u.getId());
            }
        }catch (UserFoundException uaeEx){
            LOGGER.warn(String.format("Error registering account under username %s", form.getMail()));
            mav.addObject("UserError", "An account for that username/email already exists.");
            return mav;
        }catch (PassMatchException psEx){
            LOGGER.warn("Passwords don't match");
            mav.addObject("PasswordError", "Passwords don't match");
            return mav;
        }

    }
}
