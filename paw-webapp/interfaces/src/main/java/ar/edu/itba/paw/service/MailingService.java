package ar.edu.itba.paw.service;

public interface MailingService {
        void sendMail(String replyTo, String to, String name, String message);
}
