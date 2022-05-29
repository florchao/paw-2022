package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;

public class ReviewForm {
    @NotBlank
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
