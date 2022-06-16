package ar.edu.itba.paw.service.mails;

public class RejectionMail {
    private final String subjectEs;

    private final String contentEs;

    private final String subjectEn;

    private final String contentEn;

    public RejectionMail(String title) {
        this.subjectEs = "Lamentamos informarle que no fue aceptado/a para " + title;
        this.contentEs = "<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">No has sido aceptado/a</h1>\n" +
                "<p>&iexcl;No te preocupes, hay muchas mas opciones para vos! Para ir a verlas</p>\n" +
                "  <a href=\"http://pawserver.it.itba.edu.ar/paw-2022a-02//trabajos\"><button id=\"gfg\" onmouseover=\"mouseover()\" \n" +
                "        onmouseout=\"mouseout()\" style=\"color: #581c87; background-color: #a78bfa;\n" +
                "  padding: 14px 40px;\n" +
                "  border-radius: 8px;\n" +
                "  cursor: pointer;\n" +
                "  font-family: Arial, Helvetica, sans-serif;\n" +
                "  font-size: 14px; \"> Haz click aqu&iacute; </button></a>\n" +
                "</div>";
        this.subjectEn = "We are sorry to inform you that you were not accepted for " + title;
        this.contentEn = "<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">You haven't been accepter</h1>\n" +
                "<p>Don't worry, there are many options waiting for you! To check them out</p>\n" +
                "  <a href=\"http://pawserver.it.itba.edu.ar/paw-2022a-02//trabajos\"><button id=\"gfg\" onmouseover=\"mouseover()\" \n" +
                "        onmouseout=\"mouseout()\" style=\"color: #581c87; background-color: #a78bfa;\n" +
                "  padding: 14px 40px;\n" +
                "  border-radius: 8px;\n" +
                "  cursor: pointer;\n" +
                "  font-family: Arial, Helvetica, sans-serif;\n" +
                "  font-size: 14px; \"> Click here </button></a>\n" +
                "</div>";
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
