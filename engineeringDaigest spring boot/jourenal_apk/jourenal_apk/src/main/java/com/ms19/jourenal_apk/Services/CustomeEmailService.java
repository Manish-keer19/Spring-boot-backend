package com.ms19.jourenal_apk.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomeEmailService  {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendHtmlMail(String to, String subject, String htmlContent) {
        try {
            // Create a MIME message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            // Helper to handle the message content
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            // Set the email properties
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // Set `true` for HTML content

            // Send the email
            javaMailSender.send(mimeMessage);
            log.info("Email sent successfully to {}", to);

        } catch (MessagingException e) {
            log.error("Error while sending email: {}", e.getMessage());
        }
    }
}

