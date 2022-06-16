package ar.edu.itba.paw.service.mails;

public class ContactMail {
    private final String subjectEs;

    private final String contentEs;

    private final String subjectEn;

    private final String contentEn;

    public ContactMail(String name, String replyTo) {
        this.subjectEs = "¡" + name + " quiere contactarse con vos!";
        this.contentEs= String.format("<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">&iexcl;%s te ha enviado un mensaje!</h1>\n" +
                "<p>Te envi&oacute; su informaci&oacute;n que s&oacute;lo tu puedes ver para que se puedan conectar.</p>\n" +
                "<p>Para ver este y todos tus otros mensajes</p>\n" +
                "  <a href=\"http://pawserver.it.itba.edu.ar/paw-2022a-02/contactos\"><button id=\"gfg\" onmouseover=\"mouseover()\" \n" +
                "        onmouseout=\"mouseout()\" style=\"color: #581c87; background-color: #a78bfa;\n" +
                "  padding: 14px 40px;\n" +
                "  border-radius: 8px;\n" +
                "  cursor: pointer;\n" +
                "  font-family: Arial, Helvetica, sans-serif;\n" +
                "  font-size: 14px; \"> Haz click aqu&iacute; </button></a>\n" +
                "<p style = \"font-size: 11px\">Para contestar el mensaje, puedes responder este mail o escribirle a %s</p>\n" +
                "</div>",name, replyTo);
        this.subjectEn = name + " wants to contact you!";
        this.contentEn = String.format("<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">%s has sent you a message!</h1>\n" +
                "<p>If you would like to contact this employer here you have their information that only you can see.</p>\n" +
                "<p>To see this and any other messages</p>\n" +
                "  <a href=\"http://pawserver.it.itba.edu.ar/paw-2022a-02/contactos\"><button id=\"gfg\" onmouseover=\"mouseover()\" \n" +
                "        onmouseout=\"mouseout()\" style=\"color: #581c87; background-color: #a78bfa;\n" +
                "  padding: 14px 40px;\n" +
                "  border-radius: 8px;\n" +
                "  cursor: pointer;\n" +
                "  font-family: Arial, Helvetica, sans-serif;\n" +
                "  font-size: 14px; \"> Click here </button></a>\n" +
                "<p style = \"font-size: 11px\">You can answer their message by replying this email or by sending one to %s</p>\n" +
                "</div>",name, replyTo);
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
