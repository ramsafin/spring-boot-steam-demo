package ru.kpfu.itis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.model.dto.UserDTO;
import ru.kpfu.itis.model.entity.Game;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.service.GameService;
import ru.kpfu.itis.service.UserService;

import javax.validation.Valid;
import java.security.Principal;


@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "fail", required = false) String fail,
            Model model
    ) {
        log.error("/login request");
        if (fail != null) {
            log.error("login is failed");
            //TODO don't forget about login fail
            model.addAttribute("msg", "Failed to login through Steam");
        }

        log.error("return login view");

        return "login";
    }

    @GetMapping("/synchronize")
    public String sync(OpenIDAuthenticationToken authentication) {
        User user = (User) authentication.getPrincipal();
        log.error("Update user info...");
        log.error("id : " + user.getId());
        userService.updateSteamInfo(user);
        return "redirect:/";
    }


    @GetMapping("/login/continue/{id}")
    public String continueLogin(@PathVariable Long id, Principal principal, ModelMap model) {

        if (principal != null && id != null) {
            model.addAttribute("user", new UserDTO()); //add user to the form
            model.addAttribute("id", id);
            return "login-continue";
        }

        return "redirect:/";
    }


    @PostMapping("/login/profile/perform")
    public String performLogin(
            @ModelAttribute("user") @Valid UserDTO user, BindingResult result,
            Principal principal, @RequestParam("id") Long id
    ) {

        if (principal != null) {

            if (result.hasErrors()) {
                return "login-continue";
            }

            //save user, update
            userService.saveUser(new User(id, user.getFullName(),
                    user.getAboutMe(), user.getTelephone()));
            return "redirect:/";

        }
        return "redirect:/login?fail";
    }

}