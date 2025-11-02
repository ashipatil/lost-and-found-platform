package com.example.demo.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String toEmail, String subject, String htmlContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // 'true' enables multipart and HTML

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // Enable HTML content
            helper.setFrom("lostandfoundofficials@gmail.com");
            System.out.println("✅ Async Email sent to: " + toEmail);

            javaMailSender.send(message);
        } catch (Exception e) {
        	System.err.println("❌ Async Email sending failed to: " + toEmail);
            e.printStackTrace(); 
        }
    }
}
