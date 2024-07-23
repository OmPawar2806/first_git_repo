package com.Simple_Mail_Message.simpleMail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Simple_Mail_Message.simpleMail.entity.Email;
import com.Simple_Mail_Message.simpleMail.repo.EmailRepository;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    EmailRepository repo;
    
    @Autowired
    private JavaMailSender mailsender;
    
    @Override
    public Email createEmail(Email email) {
        // Save the email entity
        Email emailSave = repo.save(email);

        // Create a SimpleMailMessage
        SimpleMailMessage message = new SimpleMailMessage();
        
        // Assuming Email entity has appropriate getters for email, subject, and body
        message.setFrom("ompawar2806mail.com");
        message.setTo(email.getRecipient()); // Assuming getRecipient() returns the reciver's email address
        BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
        String encryptEmail=bcrypt.encode(email.getRecipient());
        email.setRecipient(encryptEmail);
//      message.setTo(encryptEmail);
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        repo.save(email);
        // use to Send the email
        mailsender.send(message);
        System.out.println("Mail Sent...");

        return emailSave;
    }
    
//    public Email showDetail(Email email) {
//		
//    	return email;
//    }
}
