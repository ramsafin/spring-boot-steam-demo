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
import java.util.List;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public LoginController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
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


    //TODO remove from here
    @GetMapping("/")
    public String index(Model model, OpenIDAuthenticationToken authentication) {
        model.addAttribute("authentication", authentication);
        return "show";
    }

 //update game db
    @GetMapping("/update")
    public String update(){
        List<Game> gameList = gameService.getAllGames();
       return gameList.get(gameList.size()-29).getId().toString()+" "+ gameList.get(gameList.size()-30).getId().toString();

    };

@GetMapping("/list")
@ResponseBody
public Long games(){
    return userService.getGames().get(1);
}
    @GetMapping("/login/continue/{id}")
    public String continueLogin(@PathVariable Long id, Principal principal, ModelMap model) {

        log.error("continue controller");
        log.error("Principal : " + principal);

        if (principal != null && id != null) {
            log.error("Add userDTO and id to model");
            model.addAttribute("user", new UserDTO()); //add user to the form
            model.addAttribute("id", id);
            return "login-continue";
        }

        log.error("[redirect to /, user there is no continue for authorized user]");
        return "redirect:/";
    }


    @PostMapping("/login/profile/perform")
    public String performLogin(
            @ModelAttribute("user") @Valid UserDTO user, BindingResult result,
            Principal principal, @RequestParam("id") Long id
    ) {

        log.error("perform login controller");

        log.error("id : " + id);

        if (principal != null) {

            if (principal instanceof User) {
                log.error("[principal is instance of User]");
                User pU = (User) principal;
                log.error("User : " + pU.toString());
            }

            if (result.hasErrors()) {
                log.error("form has errors");
                return "login-continue";
            }

            log.error("updating user...");
            //save user, update
            userService.saveUser(new User(id, user.getFullName(),
                    user.getAboutMe(), user.getTelephone()));

            return "redirect:/";

        }
        return "redirect:/login?fail";
    }

}