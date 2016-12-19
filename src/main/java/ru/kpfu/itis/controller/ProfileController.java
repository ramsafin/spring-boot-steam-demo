package ru.kpfu.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.SpringUserRepository;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    private final SpringUserRepository userRepository;

    private static final String PROFILE_URL = "http://steamcommunity.com/profiles/";

    @Autowired
    public ProfileController(SpringUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profile(Principal principal, ModelMap model) {

        if (principal != null) {
            String openId = principal.getName();
            User user = userRepository.findByOpenid(openId);
            model.addAttribute("user", user);
            model.addAttribute("openId", PROFILE_URL + openId.substring(openId.lastIndexOf('/'), openId.length()));
            model.addAttribute("games", user.getGamesSet().stream().limit(5).collect(Collectors.toSet()));
            model.addAttribute("groups", user.getGroupsList().stream().limit(5).collect(Collectors.toSet()));
            model.addAttribute("avatar", user.getAvatarUrl());
        }

        return "test/profile";
    }
}
