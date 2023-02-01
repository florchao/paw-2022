package ar.edu.itba.paw.webapp.dto.RatingDto;

import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RatingCretaeDto {

    @NotNull
    private Long employeeId;
    @NotNull
    private Long employerId;

    @Min(1)
    @Max(5)
    @NotNull
    private Long rating;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }
}
