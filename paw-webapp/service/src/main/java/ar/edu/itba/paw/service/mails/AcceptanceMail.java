package ar.edu.itba.paw.service.mails;

public class AcceptanceMail {
    private final String subjectEs;

    private final String contentEs;

    private final String subjectEn;

    private final String contentEn;

    public AcceptanceMail(String title, String from) {
        this.subjectEs = "¡Felicitaciones!";
        this.contentEs = String.format("<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">&iexcl;Nos emociona contarte que has sido aceptado/a para %s!</h1>\n" +
                "<p>Nos alegra mucho esta noticia y esperamos que este trabajo sea todo lo que estas buscando.</p>\n" +
                "<p>Para conectarte con tu nuevo empleador podes contestar este mail o escribirle a %s</p>\n" +
                "</div>",title, from);
        this.subjectEn = "Congratulations!";
        this.contentEn = String.format("<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">We are glad to inform you that you have been accepted for %s!</h1>\n" +
                "<p>We hope this job is all that you are looking for.</p>\n" +
                "<p>To contact your new employer you can reply this mail or send one to %s</p>\n" +
                "</div>",title, from);
    }

    public String getSubjectEs() {
        return subjectEs;
    }

    public String getContentEs() {
        return contentEs;
    }

    public String getSubjectEn() {
        return subjectEn;
    }

    public String getContentEn() {
        return contentEn;
    }
}
