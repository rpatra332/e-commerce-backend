package com.rp.ecommercebackend.service;

import com.rp.ecommercebackend.exception.EmailFailureException;
import com.rp.ecommercebackend.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${email.from}")
    private String fromAddress;
    @Value("${app.frontend.url}")
    private String url;
    @Autowired
    private JavaMailSender javaMailSender;

    private SimpleMailMessage buildMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        return simpleMailMessage;
    }

    public void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException {
        SimpleMailMessage message = buildMailMessage();
        message.setTo(verificationToken.getUser().getEmail());
        message.setSubject("Verify your email for account activation");
        message.setText("Please follow the link below to verify your email to activation your account.\n" +
                url + "/auth/verify?token=" + verificationToken.getToken());
        try {
            javaMailSender.send(message);
        } catch(MailException ex) {
            throw new EmailFailureException();
        }
    }
}
