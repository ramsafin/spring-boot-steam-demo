package ru.kpfu.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.repository.SpringUserRepository;

import java.security.Principal;

@Controller
public class IndexController {

    private final SpringUserRepository userRepository;


    @Autowired
    public IndexController(SpringUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping(value = "/")
    public String index(
            @RequestParam(value = "fail", required = false) String fail,
            Model model, Principal principal
    ) {
        if (principal != null) {
            model.addAttribute("user", userRepository.findByOpenid(principal.getName()));
        }

        if (fail != null) {
            model.addAttribute("msg", "Failed to login through Steam");
        }

        return "index";
    }

}
