package ar.edu.itba.paw.service;

import ar.edu.itba.paw.service.mails.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class MailingServiceImpl implements MailingService{

    private final Session session;

    private final Locale locale = LocaleContextHolder.getLocale();

    private boolean isEnglish(){
        String defaultLanguage = locale.getDisplayLanguage();
        return defaultLanguage.equals("English");
    }
    @Autowired
    public MailingServiceImpl(Session session) {
        this.session = session;
    }

    @Override
    @Async
    public void sendContactMail(String replyTo, String to, String name) {
        MimeMessage mimeMessage = new MimeMessage(session);
        ContactMail contactMail = new ContactMail(name, replyTo);
        if(isEnglish())
            sendEmail(mimeMessage, Collections.singletonList(to), contactMail.getSubjectEn(), contactMail.getContentEn(), replyTo);
        else
            sendEmail(mimeMessage, Collections.singletonList(to), contactMail.getSubjectEs(), contactMail.getContentEs(), replyTo);
    }

    @Override
    @Async
    public void sendApplyMail(String to, String jobTitle, String name, long jobid) {
        MimeMessage mimeMessage = new MimeMessage(session);
        ApplyMail applyMail = new ApplyMail(name, jobTitle, jobid);
        if(isEnglish())
            sendEmail(mimeMessage, Collections.singletonList(to), applyMail.getSubjectEn(), applyMail.getContentEn(), null);
        else
            sendEmail(mimeMessage, Collections.singletonList(to), applyMail.getSubjectEs(), applyMail.getContentEs(), null);
    }

    @Override
    @Async
    public void sendContactUsMail(String name, String from, String content) {
        MimeMessage mimeMessage = new MimeMessage(session);
        ContactUsMail contactUsMail = new ContactUsMail(name, content, from);
        if(isEnglish())
            sendEmail(mimeMessage, new ArrayList<>(), contactUsMail.getSubjectEn(), contactUsMail.getContentEn(), from);
        else
            sendEmail(mimeMessage, new ArrayList<>(), contactUsMail.getSubjectEs(), contactUsMail.getContentEs(), from);
    }

    private void sendRejection(String to, String title){
        MimeMessage mimeMessage = new MimeMessage(session);
        RejectionMail rejectionMail = new RejectionMail(title);
        if(isEnglish())
            sendEmail(mimeMessage, Collections.singletonList(to), rejectionMail.getSubjectEn(), rejectionMail.getContentEn(), null);
        else
            sendEmail(mimeMessage, Collections.singletonList(to), rejectionMail.getSubjectEs(), rejectionMail.getContentEs(), null);
    }

    private void sendAcceptance(String to, String from, String title){
        MimeMessage mimeMessage = new MimeMessage(session);
        AcceptanceMail acceptanceMail = new AcceptanceMail(title, from);
        if(isEnglish())
            sendEmail(mimeMessage, Collections.singletonList(to), acceptanceMail.getSubjectEn(), acceptanceMail.getContentEn(), from);
        else
            sendEmail(mimeMessage, Collections.singletonList(to), acceptanceMail.getSubjectEs(), acceptanceMail.getContentEs(), from);
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
