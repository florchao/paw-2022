package ar.edu.itba.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MailingServiceImpl implements MailingService{

    private final Session session;
    @Autowired
    public MailingServiceImpl(Session session) {
        this.session = session;
    }

    @Override
    @Async
    public void sendContactMail(String replyTo, String to, String name) {
        MimeMessage mimeMessage = new MimeMessage(session);
        String subject = "¡"+ name + " quiere contactarse con vos!";
        String message = String.format("<div style = \"justify-content: center;\n" +
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
        sendEmail(mimeMessage, Collections.singletonList(to), subject, message, replyTo);
    }

    @Override
    @Async
    public void sendApplyMail(String to, String jobTitle, String name, long jobid) {
        MimeMessage mimeMessage = new MimeMessage(session);
        String subject = "¡"+ name + " aplicó para "+jobTitle+"!";
        String message = String.format("<div style = \"justify-content: center;\n" +
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
        sendEmail(mimeMessage, Collections.singletonList(to), subject, message, null);
    }

    @Override
    @Async
    public void sendContactUsMail(String name, String from, String content) {
        MimeMessage mimeMessage = new MimeMessage(session);
        String subject = "¡" + name + " tiene una conulta!";
        String message = String.format("<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">&iexcl;%s envió un mensaje!</h1>\n" +
                "<p style = \"font-size: 18px\">%s</p>\n" +
                "<p style = \"font-size: 11px\">Para contestar la consulta, puedes responder este mail o escribirle a %s</p>\n" +
                "</div>", name, content, from);
        sendEmail(mimeMessage, new ArrayList<>(), subject, message, from);
    }

    private void sendRejection(String to, String title){
        MimeMessage mimeMessage = new MimeMessage(session);
        String subject = "Lamentamos informarle que no fue aceptado/a para " + title;
        String message = "<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">No has sido aceptado/a</h1>\n" +
                "<p style=\"font-size: 14px;\">&iexcl;No te preocupes, hay muchas mas opciones para vos! Para ir a verlas</p>\n" +
                "  <a href=\"http://pawserver.it.itba.edu.ar/paw-2022a-02//trabajos\"><button id=\"gfg\" onmouseover=\"mouseover()\" \n" +
                "        onmouseout=\"mouseout()\" style=\"color: #581c87; background-color: #a78bfa;\n" +
                "  padding: 14px 40px;\n" +
                "  border-radius: 8px;\n" +
                "  cursor: pointer;\n" +
                "  font-family: Arial, Helvetica, sans-serif;\n" +
                "  font-size: 14px; \"> Haz click aqu&iacute; </button></a>\n" +
                "</div>";
        sendEmail(mimeMessage, Collections.singletonList(to), subject, message, null);
    }

    private void sendAcceptance(String to, String from, String title){
        MimeMessage mimeMessage = new MimeMessage(session);
        String subject = "¡Felicitaciones!";
        String message = String.format("<div style = \"justify-content: center;\n" +
                "  align-items: center;\">\n" +
                "<h1 style=\"color: #a78bfa;\">&iexcl;Nos emociona contarte que has sido aceptado/a para %s!</h1>\n" +
                "<p style=\"font-size: 14px;\">Nos alegra mucho esta noticia y esperamos que este trabajo sea todo lo que estas buscando.</p>\n" +
                "<p style=\"font-size: 14px;\">Para conectarte con tu nuevo empleador podes contestar este mail o escribirle a %s</p>\n" +
                "</div>",title, from);
        sendEmail(mimeMessage, Collections.singletonList(to), subject, message, from);
    }

    @Override
    @Async
    public void sendChangeStatus(int status, String to, String from, String title) {
        if(status == 1)
            sendAcceptance(to, from, title);
        else if (status == 2) {
            sendRejection(to, title);
        }
    }

    private void sendEmail(Message mimeMessage, List<String> to, String subject, String message, String from) {
        try {
            String SERVER_MAIL = "hogarempleos22@gmail.com";
            String hogar = String.format("\"Hogar\" <%s>", SERVER_MAIL);
            mimeMessage.setFrom(new InternetAddress(hogar));
            for (String destination : to) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            }
            if(to.isEmpty())
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(SERVER_MAIL));
            mimeMessage.setSubject(subject);
            if(from == null)
                mimeMessage.setReplyTo(new Address[]{new InternetAddress(SERVER_MAIL)});
            else
                mimeMessage.setReplyTo(new Address[]{new InternetAddress(from)});
            mimeMessage.setContent(message, "text/html");
            Transport.send(mimeMessage);
        } catch (MessagingException mex) {
            throw new RuntimeException(mex.getMessage());
        }
    }


}
