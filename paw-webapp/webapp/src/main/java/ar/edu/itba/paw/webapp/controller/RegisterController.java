package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.ExperienceService;
import ar.edu.itba.paw.service.MailingService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private MailingService mailingService;

    @RequestMapping("/registrarse")
    public ModelAndView register(@ModelAttribute("registerForm") final RegisterForm form){
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public ModelAndView registerUser(@Valid @ModelAttribute("registerForm") final RegisterForm form, final BindingResult errors){

        ModelAndView mav = new ModelAndView("register");

        if (errors.hasErrors()){
            return register(form);
        }
        try{
            int role = form.getRole().equals("Empleada")? 1 : 2;
            final User u = userService.create(form.getMail(), form.getPassword(), form.getConfirmPassword(), role);
            if(role == 1)
                return new ModelAndView("redirect:/crearPerfil/"+ u.getId());
            else
                return new ModelAndView("redirect:/crearPerfilEmpleador/" + u.getId());
        }catch (UserFoundException uaeEx){
            mav.addObject("UserError", "An account for that username/email already exists.");
            return mav;
        }catch (PassMatchException psEx){
            mav.addObject("PasswordError", "Passwords don't match");
            return mav;
        }

    }

}
