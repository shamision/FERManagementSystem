package com.project.FERMS.services;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String message);
}
