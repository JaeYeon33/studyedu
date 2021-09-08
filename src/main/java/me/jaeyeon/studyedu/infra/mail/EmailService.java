package me.jaeyeon.studyedu.infra.mail;


public interface EmailService {

    void sendEmail(EmailMessage emailMessage);
}
