package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.ExperienceService;
import ar.edu.itba.paw.service.MailingService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sun.jvm.hotspot.asm.Register;

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
    public ModelAndView register(){
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public ModelAndView registerUser(@Valid @ModelAttribute("register") final RegisterForm form, final BindingResult errors){
        System.out.println("MAIL");
        System.out.println(form.getMail());
        System.out.println("PASSWORD");
        System.out.println(form.getPassword());
        if (errors.hasErrors()){
            System.out.println("ERROR");
            return register();
        }

        final User u = userService.create(form.getMail(), form.getPassword());
        System.out.println(u.toString());
        return new ModelAndView("redirect:/crearPerfil");
    }

}
