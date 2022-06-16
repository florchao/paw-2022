package ar.edu.itba.paw.service.mails;

public class ApplyMail {
    private final String subjectEs;

    private final String contentEs;

    private final String subjectEn;

    private final String contentEn;

    public ApplyMail(String name, String jobTitle, long jobid) {
        this.subjectEs =  "¡"+ name + " aplicó para "+jobTitle+"!";
        this.contentEs = String.format("<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">&iexcl;%s ha aplicado para trabajar con vos!</h1>\n" +
                "<p>Para ver su informaci&oacute;n y el de todos los aplicantes.</p>\n" +
                "  <a href=\"http://pawserver.it.itba.edu.ar/paw-2022a-02/aplicantes/%s\"><button id=\"gfg\" onmouseover=\"mouseover()\" \n" +
                "        onmouseout=\"mouseout()\" style=\"color: #581c87; background-color: #a78bfa;\n" +
                "  padding: 14px 40px;\n" +
                "  border-radius: 8px;\n" +
                "  cursor: pointer;\n" +
                "  font-family: Arial, Helvetica, sans-serif;\n" +
                "  font-size: 14px; \"> Haz click aqu&iacute; </button></a>\n" +
                "</div>",name, jobid);
        this.subjectEn = name + " applied for "+jobTitle+"!";
        this.contentEn = String.format("<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">%s applied to work with you!</h1>\n" +
                "<p>To see their information or any other applicants information</p>\n" +
                "  <a href=\"http://pawserver.it.itba.edu.ar/paw-2022a-02/aplicantes/%s\"><button id=\"gfg\" onmouseover=\"mouseover()\" \n" +
                "        onmouseout=\"mouseout()\" style=\"color: #581c87; background-color: #a78bfa;\n" +
                "  padding: 14px 40px;\n" +
                "  border-radius: 8px;\n" +
                "  cursor: pointer;\n" +
                "  font-family: Arial, Helvetica, sans-serif;\n" +
                "  font-size: 14px; \">Click here </button></a>\n" +
                "</div>",name, jobid);
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
