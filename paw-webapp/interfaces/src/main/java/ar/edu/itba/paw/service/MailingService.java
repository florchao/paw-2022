package ar.edu.itba.paw.service;

import java.util.Locale;

public interface MailingService {
        void sendContactMail(String replyTo, String to, String name, Locale language);
        void sendApplyMail(String to, String jobTitle, String name, long jobid, Locale language);
        void sendContactUsMail(String name, String from, String content, Locale language);
        void sendChangeStatus(int status, String to, String from, String title, Locale language);
}
