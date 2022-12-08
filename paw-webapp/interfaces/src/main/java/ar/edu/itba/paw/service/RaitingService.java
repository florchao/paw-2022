package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;

public interface RaitingService {
    boolean hasAlreadyRated(long idRating, long userID);

    float getRating(long employeeId);
    float updateRating(long employeeId, Long rating, Long employerId);

}
