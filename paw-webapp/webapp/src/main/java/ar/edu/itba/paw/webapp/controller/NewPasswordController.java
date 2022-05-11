package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.NewPasswordForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(NewPasswordController.class);

    @RequestMapping(value = "/nuevaContrasena", method = {RequestMethod.GET})
    public ModelAndView newPassword(@ModelAttribute("newPasswordForm") final NewPasswordForm form){
        return new ModelAndView("newPassword");
    }

    @RequestMapping(value = "/newPassword", method = {RequestMethod.POST})
    public ModelAndView updatePassword(@Valid @ModelAttribute("newPasswordForm") final NewPasswordForm form, final BindingResult errors){
        if (errors.hasErrors()){
            LOGGER.debug("couldn't update password");
            return newPassword(form);
        }
        userService.update(form.getMail(), form.getPassword());
        LOGGER.debug("password updated");
        return new ModelAndView("redirect:/buscarEmpleadas");
    }
}
