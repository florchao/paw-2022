package ar.edu.itba.paw.service.mails;

public class ContactUsMail {
    private final String subjectEs;

    private final String contentEs;

    private final String subjectEn;

    private final String contentEn;

    public ContactUsMail(String name, String content, String from) {
        this.subjectEs = "¡" + name + " tiene una conulta!";
        this.contentEs = String.format("<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">&iexcl;%s envió un mensaje!</h1>\n" +
                "<p style = \"font-size: 18px\">%s</p>\n" +
                "<p style = \"font-size: 11px\">Para contestar la consulta, puedes responder este mail o escribirle a %s</p>\n" +
                "</div>", name, content, from);
        this.subjectEn = name + " has an inquiry!";
        this.contentEn = String.format("<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">%s has sent you a message!</h1>\n" +
                "<p style = \"font-size: 18px\">%s</p>\n" +
                "<p style = \"font-size: 11px\">You can answer their message by replying this email or by sending one to %s</p>\n" +
                "</div>", name, content, from);
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
