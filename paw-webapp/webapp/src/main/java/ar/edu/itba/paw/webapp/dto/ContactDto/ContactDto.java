package ar.edu.itba.paw.webapp.dto.ContactDto;


import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.webapp.dto.DtoUtils;
import ar.edu.itba.paw.webapp.dto.EmployerDto;

import javax.ws.rs.core.UriInfo;

public class ContactDto {

    private EmployerDto employer;
    private String message;
    private String phoneNumber;
    private String created;

    public static ContactDto fromContact(final UriInfo uriInfo, final Contact contact) {
        final ContactDto dto = new ContactDto();

        dto.employer = EmployerDto.fromContact(uriInfo, contact.getEmployerID());
        dto.message = contact.getMessage();
        dto.phoneNumber = contact.getPhoneNumber();
        dto.created = DtoUtils.dateToString(contact.getCreated());

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
