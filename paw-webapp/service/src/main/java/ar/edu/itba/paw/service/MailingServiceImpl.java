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
    public void sendMail(String replyTo, String to, String name, String message) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);

            String SERVER_MAIL = "hogarempleos22@gmail.com";
            String from = String.format("\"%s\" <%s>", replyTo.substring(0, replyTo.indexOf('@')), SERVER_MAIL);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setReplyTo(new Address[]{new InternetAddress(replyTo)});
            String subject = "¡"+ name + " quiere contactarse con vos!";
            sendEmail(mimeMessage, Collections.singletonList(to), subject, message);
        } catch (MessagingException mex) {
            throw new RuntimeException(mex.getMessage());
        }
    }

    private void sendEmail(Message mimeMessage, List<String> to, String subject, String message) {
        try {
            for (String destination : to) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            }
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(message, "text/plain");
            Transport.send(mimeMessage);
        } catch (MessagingException mex) {
            throw new RuntimeException(mex.getMessage());
        }
    }
}
