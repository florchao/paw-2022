package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exception.ContactException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MailingServiceImpl implements MailingService {

    private final Session session;

    @Autowired
    public MessageSource messageSource;

    @Autowired
    public MailingServiceImpl(Session session) {
        this.session = session;
    }

    @Override
    @Async
    public void sendContactMail(String replyTo, String to, String name) {
        MimeMessage mimeMessage = new MimeMessage(session);
        String content = messageSource.getMessage("contactMail.text", new Object[]{name, replyTo}, LocaleContextHolder.getLocale());
        String subject = messageSource.getMessage("contactMail.subject", new Object[]{name}, LocaleContextHolder.getLocale());
        sendEmail(mimeMessage, Collections.singletonList(to), subject, content, replyTo);
    }

    @Override
    @Async
    public void sendApplyMail(String to, String jobTitle, String name, long jobid) {
        MimeMessage mimeMessage = new MimeMessage(session);
        String content = messageSource.getMessage("applyMail.text", new Object[]{name, jobid}, LocaleContextHolder.getLocale());
        String subject = messageSource.getMessage("applyMail.subject", new Object[]{name, jobTitle}, LocaleContextHolder.getLocale());
        sendEmail(mimeMessage, Collections.singletonList(to), subject, content, null);
    }

    @Override
    @Async
    public void sendContactUsMail(String name, String from, String content) {
        MimeMessage mimeMessage = new MimeMessage(session);
        String question = messageSource.getMessage("contactUsMail.text", new Object[]{name, content, from}, LocaleContextHolder.getLocale());
        String subject = messageSource.getMessage("contactUsMail.subject", new Object[]{name}, LocaleContextHolder.getLocale());
        sendEmail(mimeMessage, new ArrayList<>(), subject, question, from);
    }

    private void sendRejection(String to, String title) {
        MimeMessage mimeMessage = new MimeMessage(session);
        String content = messageSource.getMessage("rejectionMail.text", null, LocaleContextHolder.getLocale());
        String subject = messageSource.getMessage("rejectionMail.subject", new Object[]{title}, LocaleContextHolder.getLocale());
        sendEmail(mimeMessage, Collections.singletonList(to), subject, content, null);
    }

    private void sendAcceptance(String to, String from, String title) {
        MimeMessage mimeMessage = new MimeMessage(session);
        String content = messageSource.getMessage("acceptanceMail.text", new Object[]{title, from}, LocaleContextHolder.getLocale());
        String subject = messageSource.getMessage("acceptanceMail.subject", null, LocaleContextHolder.getLocale());
        sendEmail(mimeMessage, Collections.singletonList(to), subject, content, from);
    }

    @Override
    @Async
    public void sendChangeStatus(int status, String to, String from, String title) {
        if (status == 1)
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
            if (to.isEmpty()) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(SERVER_MAIL));
            }
            mimeMessage.setSubject(subject);
            if (from == null) {
                mimeMessage.setReplyTo(new Address[]{new InternetAddress(SERVER_MAIL)});
            } else {
                mimeMessage.setReplyTo(new Address[]{new InternetAddress(from)});
            }
            mimeMessage.setContent(message, "text/html");
            Transport.send(mimeMessage);
        } catch (MessagingException mex) {
            throw new ContactException("couldn't make contact from" + from + "caused by" + mex.getMessage(), mex);
        }
    }

}
