package ru.kpfu.itis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kpfu.itis.model.entity.Chat;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.SpringChatRepository;
import ru.kpfu.itis.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final UserService userService;

    private final SpringChatRepository chatRepository;

    @Autowired
    public ChatController(UserService userService, SpringChatRepository chatRepository) {
        this.userService = userService;
        this.chatRepository = chatRepository;
    }

    @GetMapping("/chat")
    public String chat(Principal principal, ModelMap modelMap) {

        log.error("[Chat request]");

        User user;

        //TODO if principal is null then get user with id = 2
        if (principal == null) {

            log.error("user is anonymous");
            user = userService.findOne(2L); //delete this in production

        } else {
            log.error("Found user " + principal.getName());
            user = userService.findUserByToken(principal.getName());
        }

        List<Chat> chatList = chatRepository.findAll(user.getId());

        if (chatList == null || chatList.size() == 0) {
            chatList = Collections.emptyList();
        }

        modelMap.addAttribute("chats", chatList);

        return "test/chat";
    }


    @GetMapping("/chat/{chatId}")
    public String chatRoom(@PathVariable Long chatId, ModelMap modelMap, Principal principal, HttpServletResponse resp) {
        log.error("[chat room ], room id " + chatId);
        if (principal != null) {
            resp.addCookie(new Cookie("user", principal.getName()));
        }
        modelMap.addAttribute("chatRoom", chatId);
        return "chatRoom";
    }

}
