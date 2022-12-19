package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.EmployerService;
import ar.edu.itba.paw.webapp.dto.ContactDto;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/api/contacts")
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
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response contactUs(@FormDataParam("name") String name,
                              @FormDataParam("mail") String mail,
                              @FormDataParam("content") String content) {
        if( name.isEmpty() || !name.matches("[a-zA-z\\s]+|^$") || name.length() > 100
        || mail.isEmpty() || !mail.matches("[\\w-+_.]+@([\\w]+.)+[\\w]{1,100}") || content.isEmpty())
            Response.status(Response.Status.BAD_REQUEST).build();
        //todo check que pasa el post con error con sotuyo
        contactService.contactUS(content, mail, name);
        return Response.status(Response.Status.CREATED).build();
    }

    //TODO: los ids van por body en el POST
    @POST
    @Path("")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    public Response contactEmployee(@FormDataParam("content") String content,
                                    @FormDataParam("phone") String phone,
                                    @FormDataParam("employee_id") Long employeeId,
                                    @FormDataParam("employer_id") Long employerId) throws AlreadyExistsException {
//        if(error.hasErrors()) {
//            LOGGER.debug("Couldn't contact Hogar");
//            //return contactPage(form, "error");
//        }
        if(content.isEmpty() || !phone.matches("[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*")
        || Objects.isNull(employeeId) || Objects.isNull(employerId) || employeeId.equals(employerId)){
            Response.status(Response.Status.BAD_REQUEST).build();
        }

        Employee employee;
        Employer employer;
        try{
           employee = employeeService.getEmployeeById(employeeId);
           employer = employerService.getEmployerById(employerId);
        }catch (UserNotFoundException u){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String name = employee.firstWordsToUpper();
        boolean exists = contactService.contact(employee.getId(), employer.getId(), content, name, phone);
        if (exists) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }


    //TODO: al tener autorización vamos a poder chequear esto cuando traemos las empleadas y nos ahorramos una llamada a la API
    @GET
    @Path("/{employeeId}/{employerId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response existsContact(@PathParam("employeeId") long employeeId, @PathParam("employerId") long employerId) throws UserNotFoundException {
        List<ContactDto> exists;
        try {
            exists = contactService.existsContact(employeeId, employerId).stream().map(c -> ContactDto.fromContact(uriInfo, c)).collect(Collectors.toList());
        } catch (Exception ex){
            ex.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        GenericEntity<List<ContactDto>> genericEntity = new GenericEntity<List<ContactDto>>(exists) {
        };
        return Response.ok(genericEntity).build();
    }

}
