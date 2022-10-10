package com.itsol.recruit.service;

public interface EmailService {
    public void sendEmail(String toEmail, String subject, String body);
}
