package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.EmployerService;
import ar.edu.itba.paw.webapp.dto.ContactDto.ContactCreateDto;
import ar.edu.itba.paw.webapp.dto.ContactDto.ContactDto;
import ar.edu.itba.paw.webapp.dto.ContactDto.ContactUsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Path("/contacts")
@Component
public class ContactController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private ContactService contactService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @POST
    @Path("/us")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response contactUs(@Valid ContactUsDto contactUsDto,
                              @Context HttpServletRequest request) {
//        if( name.isEmpty() || !name.matches("[a-zA-z\\s]+|^$") || name.length() > 100
//        || mail.isEmpty() || !mail.matches("[\\w-+_.]+@([\\w]+.)+[\\w]{1,100}") || content.isEmpty())
//            Response.status(Response.Status.BAD_REQUEST).build();

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 2));
        LocaleContextHolder.setLocale(locale);

        contactService.contactUS(contactUsDto.getContent(), contactUsDto.getMail(), contactUsDto.getName());
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response contactEmployee(@Valid ContactCreateDto contactCreateDto,
                                    @Context HttpServletRequest request) throws AlreadyExistsException {
//        if(content.isEmpty() || !phone.matches("[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*")
//        || Objects.isNull(employeeId) || Objects.isNull(employerId) || employeeId.equals(employerId)){
//            Response.status(Response.Status.BAD_REQUEST).build();
//        }

        Locale locale = new Locale(request.getHeader("Accept-Language").substring(0, 2));
        LocaleContextHolder.setLocale(locale);

        Employee employee;
        Employer employer;
        try{
           employee = employeeService.getEmployeeById(contactCreateDto.getEmployeeId());
           employer = employerService.getEmployerById(contactCreateDto.getEmployerId());
        }catch (UserNotFoundException u){
            LOGGER.error("User not found", u);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String name = employer.firstWordsToUpper();
        boolean exists = contactService.contact(employee.getId(), employer.getId(), contactCreateDto.getContent(), name, contactCreateDto.getPhone());
        if (exists) {
            LOGGER.error(String.format("The user %d has already contacted user %d", employer.getId().getId(), employee.getId().getId()));
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }


    @GET
    @Path("/{employeeId}/{employerId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response existsContact(@PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId) throws UserNotFoundException {
        List<ContactDto> exists;
        try {
            exists = contactService.existsContact(employeeId, employerId).stream().map(c -> ContactDto.fromContact(uriInfo, c)).collect(Collectors.toList());
        } catch (UserNotFoundException exception) {
            LOGGER.error("an exception occurred:", exception);
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception exception) {
            LOGGER.error("an exception occurred:", exception);
            return Response.status(Response.Status.CONFLICT).build();
        }
        GenericEntity<List<ContactDto>> genericEntity = new GenericEntity<List<ContactDto>>(exists) {
        };
        return Response.ok(genericEntity).build();
    }

}
