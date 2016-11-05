package ru.kpfu.itis;

import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {


    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping("/")
    public String show(Model model, OpenIDAuthenticationToken authentication) {
        model.addAttribute("authentication", authentication);
        return "show";
    }
}