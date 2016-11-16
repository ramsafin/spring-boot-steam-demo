package ru.kpfu.itis.controller;

import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "fail", required = false) String fail,
            Model model
    ) {
        if (fail != null) {
            model.addAttribute("msg", "Failed to login through Steam");
        }

        return "login";
    }


    @GetMapping("/")
    public String show(Model model, OpenIDAuthenticationToken authentication) {
        model.addAttribute("authentication", authentication);
        return "show";
    }
}