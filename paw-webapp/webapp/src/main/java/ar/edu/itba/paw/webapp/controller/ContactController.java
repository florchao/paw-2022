package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.EmployerService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.ContactDto;
import ar.edu.itba.paw.webapp.form.ContactUsForm;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/contacts")
@Component
public class ContactController {

    private final int PAGE_SIZE = 8;
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private ContactService contactService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

//    @RequestMapping(value = "/contactos", method = {RequestMethod.GET})
//    public ModelAndView contactsPage(@RequestParam(value = "page", required = false) Long page) throws UserNotFoundException {
//        final ModelAndView mav = new ModelAndView("contacts");
//        if (page == null)
//            page = 0L;
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        List<Contact> list = contactService.getAllContacts(principal.getUserID(), page, PAGE_SIZE);
//        for (Contact contact : list) {
//            contact.firstWordsToUpper();
//
//        }
//        mav.addObject("ContactList", list);
//        mav.addObject("page", page);
//        int maxPage = contactService.getPageNumber(principal.getUserID(), PAGE_SIZE);
//        mav.addObject("maxPage", maxPage);
//        return mav;
//    }

//    @RequestMapping(value = "/contacto/{id}", method = {RequestMethod.GET})
//    public ModelAndView contactPage(@ModelAttribute("contactForm") final ContactForm form, @PathVariable final int id) throws UserNotFoundException {
//        final ModelAndView mav = new ModelAndView("contactForm");
//        try {
//            userService.getUserById(id);
//        } catch (ar.edu.itba.paw.model.exception.UserNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        employeeService.isEmployee(id);
//        Optional<Employee> employee = employeeService.getEmployeeById(id);
//        if(employee.isPresent()){
//            employee.get().firstWordsToUpper();
//            mav.addObject("name", employee.get().getName());
//        }
//        return mav;
//    }

//    @RequestMapping(value = "/contactEmployee/{id}", method = {RequestMethod.POST})
//    public ModelAndView contactEmployee(@Valid @ModelAttribute("contactForm") final ContactForm form, final BindingResult errors, @PathVariable int id) throws UserNotFoundException, AlreadyExistsException {
//        ModelAndView mav = new ModelAndView("redirect:/verPerfil/"+id);
//        if(errors.hasErrors()) {
//            LOGGER.debug("Couldn't contact employee");
//            return contactPage(form, id);
//        }
//        User user = userService.getUserById(id);
//        HogarUser principal = (HogarUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(contactService.contact(user, form.getContent(), principal.getName(), form.getPhone()))
//            mav.addObject("status", "sent");
//        else
//            mav.addObject("status", "error");
//        return mav;
//    }

//    @RequestMapping(value = "/contactanos", method = {RequestMethod.GET})
//    public ModelAndView contactPage(@ModelAttribute("contactUsForm") final ContactUsForm form, @RequestParam(value = "status", required = false) String status) {
//        final ModelAndView mav = new ModelAndView("contactUs");
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(principal instanceof HogarUser){
//            HogarUser hogarUser = (HogarUser) principal;
//            hogarUser.firstWordsToUpper();
//            mav.addObject("name", hogarUser.getName());
//            mav.addObject("mail", hogarUser.getUsername());
//        }
//        mav.addObject("status", status);
//        return mav;
//    }

    @POST
    @Path("/us")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response contactUs(@Valid ContactUsForm form) {
//        if(error.hasErrors()) {
//            LOGGER.debug("Couldn't contact Hogar");
//            //return contactPage(form, "error");
//        }
        contactService.contactUS(form.getContent(), form.getMail(), form.getName());
        return Response.status(Response.Status.CREATED).build();
    }

    //TODO: los ids van por body en el POST
    @POST
    @Path("")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response contactEmployee(@FormDataParam("content") String content,
                                    @FormDataParam("phone") String phone,
                                    @FormDataParam("employee_id") Long employeeId,
                                    @FormDataParam("employer_id") Long employerId) throws UserNotFoundException, AlreadyExistsException {
//        if(error.hasErrors()) {
//            LOGGER.debug("Couldn't contact Hogar");
//            //return contactPage(form, "error");
//        }
        if(content.isEmpty() || !phone.matches("[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*")
        || Objects.isNull(employeeId) || Objects.isNull(employerId) || employeeId.equals(employerId)){
            Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            userService.getUserById(employeeId);
        } catch (ar.edu.itba.paw.model.exception.UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        employeeService.isEmployee(employeeId);
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        Optional<Employer> employer = employerService.getEmployerById(employerId);
        if (employee.isPresent() && employer.isPresent()) {
            String name = employee.get().firstWordsToUpper();
            boolean exists = contactService.contact(employee.get().getId(), employer.get().getId(), content, name, phone);
            if (exists) {
                return Response.ok(1).build();
            }
            return Response.status(Response.Status.CREATED).entity(0).build();
        }
        return Response.serverError().build();
    }


    //TODO: al tener autorización vamos a poder chequear esto cuando traemos las empleadas y nos ahorramos una llamada a la API
    @GET
    @Path("/{employeeId}/{employerId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response existsContact(@PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId) throws UserNotFoundException {
        List<ContactDto> exists = contactService.existsContact(employeeId, employerId).stream().map(c -> ContactDto.fromContact(uriInfo, c)).collect(Collectors.toList());
        GenericEntity<List<ContactDto>> genericEntity = new GenericEntity<List<ContactDto>>(exists) {
        };
        return Response.ok(genericEntity).build();
    }

}
