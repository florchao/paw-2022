package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.EmployerService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;

@Controller
public class InitController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployerService employerService;


    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView helloWorld() {
        Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if(auth.contains(new SimpleGrantedAuthority("EMPLOYEE")))
            return new ModelAndView("redirect:/trabajos");
        if(auth.contains(new SimpleGrantedAuthority("EMPLOYER")))
            return new ModelAndView("redirect:/buscarEmpleadas");
        return new ModelAndView("init");
    }


    @RequestMapping(value = "/afterLogin", method = {RequestMethod.GET})
    public ModelAndView afterLogin() {
        Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(auth.contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
            Optional<Employee> employee = employeeService.getEmployeeById(principal.getUserID());
            if(!employee.isPresent()){
                return new ModelAndView("redirect:/crearPerfil/"+principal.getUserID());
            }
            return new ModelAndView("redirect:/trabajos");
        }
        else {
            Optional<Employer> employer = employerService.getEmployerById(principal.getUserID());
            if (!employer.isPresent()) {
                return new ModelAndView("redirect:/crearPerfilEmpleador/"+principal.getUserID());
            }
            return new ModelAndView("redirect:/buscarEmpleadas");
        }
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("login");
    }

}
