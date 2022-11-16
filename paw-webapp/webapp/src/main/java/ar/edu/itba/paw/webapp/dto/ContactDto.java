package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.model.Contact;

import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContactDto {

    private EmployerDto employer;
    private String message;
    private String phoneNumber;
    private String created;

    public static ContactDto fromContact(final UriInfo uriInfo, final Contact contact) {
        ContactDto dto = new ContactDto();

        dto.employer = EmployerDto.fromContact(uriInfo, contact.getEmployerID());
        dto.message = contact.getMessage();
        dto.phoneNumber = contact.getPhoneNumber();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dto.created = formatter.format(contact.getCreated());

        return dto;
    }

    public EmployerDto getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerDto employer) {
        this.employer = employer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
