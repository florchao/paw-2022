package ar.edu.itba.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    public void sendMail(String replyTo, String to, String name) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);

            String SERVER_MAIL = "hogarempleos22@gmail.com";
            String from = String.format("\"%s\" <%s>", replyTo.substring(0, replyTo.indexOf('@')), SERVER_MAIL);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setReplyTo(new Address[]{new InternetAddress(replyTo)});
            String subject = "¡"+ name + " quiere contactarse con vos!";
            sendEmail(mimeMessage, Collections.singletonList(to), subject, name);
        } catch (MessagingException mex) {
            throw new RuntimeException(mex.getMessage());
        }
    }

    private void sendEmail(Message mimeMessage, List<String> to, String subject, String name) {
        try {
            for (String destination : to) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            }
            mimeMessage.setSubject(subject);
            String message = String.format("<script>\n" +
                    "            function mouseover() {\n" +
                    "                document.getElementById(\"gfg\").style.backgroundColor = \"#8b5cf6\";\n" +
                    "            }\n" +
                    "              \n" +
                    "            function mouseout() {\n" +
                    "                document.getElementById(\"gfg\").style.backgroundColor = \"#a78bfa\";\n" +
                    "            }\n" +
                    "</script>\n" +
                    "\n" +
                    "<div style = \"justify-content: center;\n" +
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
            mimeMessage.setContent(message, "text/html");
            Transport.send(mimeMessage);
        } catch (MessagingException mex) {
            throw new RuntimeException(mex.getMessage());
        }
    }


}
