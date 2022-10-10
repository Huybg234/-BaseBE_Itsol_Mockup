package com.itsol.recruit.service.impl;

import com.itsol.recruit.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    JavaMailSender mail;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("davisdgea2001@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mail.send(message);
        System.out.println("Mail Send...");
    }
}
