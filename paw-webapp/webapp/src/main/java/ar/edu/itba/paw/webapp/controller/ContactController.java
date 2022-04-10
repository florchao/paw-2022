package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.ExperienceService;
import ar.edu.itba.paw.service.MailingService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ContactController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private MailingService mailingService;

    @RequestMapping(value = "/contactRedirect", method = RequestMethod.GET)
    public ModelAndView contactRedirect(@RequestParam("userId") Long id) {
        final ModelAndView mav = new ModelAndView("redirect:/contacto");
        mav.addObject("user",userService.getUserById(id));
        return mav;
    }

    @RequestMapping("/contacto")
    public ModelAndView contactPage(@ModelAttribute("contactForm") final ContactForm form) {
        final ModelAndView mav = new ModelAndView("contactForm2");
        return mav;
    }

    @RequestMapping(value = "/contactEmployee", method = {RequestMethod.POST})
    public ModelAndView contactEmployee(@Valid @ModelAttribute("contactForm") final ContactForm form, final BindingResult errors, @RequestParam("userId") Long id) {
        if(errors.hasErrors())
            return contactPage(form);
        return new ModelAndView("redirect:/contacto");
    }

    @RequestMapping("/chau")
    public ModelAndView goodbyeWorld() {
        final ModelAndView mav = new ModelAndView("byebye");
        mailingService.sendMail();
//        mav.addObject("user", userService.getUserById(1).orElseThrow(UserNotFoundException::new));
        return mav;
    }
}
