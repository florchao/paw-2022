package ar.edu.itba.paw.service;

public interface MailingService {
        void sendContactMail(String replyTo, String to, String name);
        void sendApplyMail(String to, String jobTitle, String name, long jobid);
        void sendContactUsMail(String name, String from, String content);
        void sendChangeStatus(int status, String to, String from, String title);
}
