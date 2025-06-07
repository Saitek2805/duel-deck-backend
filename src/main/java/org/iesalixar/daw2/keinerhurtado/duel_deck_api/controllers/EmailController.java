package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Endpoint para enviar un correo simple
    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        emailService.sendSimpleEmail(to, subject, text);
        return "Correo enviado correctamente a " + to;
    }

    // Endpoint para enviar un correo con adjunto
    @GetMapping("/send-email-with-attachment")
    public String sendEmailWithAttachment(@RequestParam String to, @RequestParam String subject,
                                          @RequestParam String text, @RequestParam String attachmentPath) {
        emailService.sendEmailWithAttachment(to, subject, text, attachmentPath);
        return "Correo con adjunto enviado correctamente a " + to;
    }
}
