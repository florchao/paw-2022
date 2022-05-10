package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.NewPasswordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class NewPasswordController {
    @Autowired
    private UserService userService;

    @RequestMapping("/nuevaContrasena")
    public ModelAndView newPassword(@ModelAttribute("newPasswordForm") final NewPasswordForm form){
        return new ModelAndView("newPassword");
    }

    @RequestMapping(value = "/newPassword", method = {RequestMethod.POST})
    public ModelAndView updatePassword(@Valid @ModelAttribute("newPasswordForm") final NewPasswordForm form, final BindingResult errors){
        if (errors.hasErrors()){
            return newPassword(form);
        }
        final boolean u = userService.update(form.getMail(), form.getPassword());
        return new ModelAndView("redirect:/buscarEmpleadas");
    }
}
