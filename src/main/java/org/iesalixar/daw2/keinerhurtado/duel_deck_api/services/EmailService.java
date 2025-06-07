package org.iesalixar.daw2.keinerhurtado.duel_deck_api.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.File;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${MAIL_FROM}")
    private String from;

    // Método para enviar un correo simple
    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Maneja la excepción adecuadamente
        }
    }

    // Método para enviar correo con adjunto
    public void sendEmailWithAttachment(String to, String subject, String text, String attachmentPath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            File file = new File(attachmentPath);
            helper.addAttachment("Attachment", file);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Maneja la excepción adecuadamente
        }
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            // Enviar el correo
            mailSender.send(message);
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
            // Aquí podrías manejar la excepción de acuerdo a lo que desees hacer en caso de fallo.
        }
    }
}
