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
        try {
            MimeMessage mimeMessage = new MimeMessage(session);

            String SERVER_MAIL = "hogarempleos22@gmail.com";
            String from = String.format("\"%s\" <%s>", replyTo.substring(0, replyTo.indexOf('@')), SERVER_MAIL);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setReplyTo(new Address[]{new InternetAddress(replyTo)});
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
                    "</div>",name);
            sendEmail(mimeMessage, Collections.singletonList(to), subject, message);
        } catch (MessagingException mex) {
            throw new RuntimeException(mex.getMessage());
        }
    }

    @Override
    @Async
    public void sendApplyMail(String to, String jobTitle, String name) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            String SERVER_MAIL = "hogarempleos22@gmail.com";
            mimeMessage.setFrom(new InternetAddress(SERVER_MAIL));
            String subject = "¡"+ name + " aplicó para "+jobTitle+"!";
            String message = String.format("<div style = \"justify-content: center;\n" +
                    "  align-items: center;\">\n" +
                    "<h1 style=\"color: #a78bfa;\">&iexcl;%s ha aplicado para trabajar con vos!</h1>\n" +
                    "<p>Para ver su informaci&oacute;n y el de todos los aplicantes.</p>\n" +
                    "  <a href=\"http://pawserver.it.itba.edu.ar/paw-2022a-02/trabajos\"><button id=\"gfg\" onmouseover=\"mouseover()\" \n" +
                    "        onmouseout=\"mouseout()\" style=\"color: #581c87; background-color: #a78bfa;\n" +
                    "  padding: 14px 40px;\n" +
                    "  border-radius: 8px;\n" +
                    "  cursor: pointer;\n" +
                    "  font-family: Arial, Helvetica, sans-serif;\n" +
                    "  font-size: 14px; \"> Haz click aqu&iacute; </button></a>\n" +
                    "</div>",name);
            sendEmail(mimeMessage, Collections.singletonList(to), subject, message);
        }catch (MessagingException mex){
            throw new RuntimeException(mex.getMessage());
        }
    }

    @Override
    public void sendContactUsMail(String name, String from, String message) {

    }

    private void sendEmail(Message mimeMessage, List<String> to, String subject, String message) {
        try {
            for (String destination : to) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            }
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(message, "text/html");
            Transport.send(mimeMessage);
        } catch (MessagingException mex) {
            throw new RuntimeException(mex.getMessage());
        }
    }


}
