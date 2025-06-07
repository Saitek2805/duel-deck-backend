package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;


import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String login(Model model) {
        return "login"; // Redirige a una plantilla personalizada de login
    }
}
