package ar.edu.itba.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailingServiceImpl implements MailingService{

    private final Session session;
    @Autowired
    public MailingServiceImpl(Session session) {
        this.session = session;
    }

    @Override
    public void sendMail(){
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hogarempleos22@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("solkonfe@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Test Mail");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
