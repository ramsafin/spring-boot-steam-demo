package ru.kpfu.itis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.model.entity.Chat;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.SpringChatRepository;
import ru.kpfu.itis.repository.SpringUserRepository;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final SpringUserRepository userRepository;

    private final SpringChatRepository chatRepository;

    @Autowired
    public ChatController(SpringUserRepository userRepository, SpringChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }


    @GetMapping("/dialogs")
    public String chat(Principal principal, ModelMap modelMap) {

        User user;

        if (principal == null) {
            user = userRepository.findUserById(2L); //TODO delete

        } else {
            user = userRepository.findUserByOpenid(principal.getName());
        }

        List<Chat> chatList = user.getChatList(); //fetch chats

        modelMap.addAttribute("chats", chatList);

        if (chatList.size() > 0) {

            modelMap.addAttribute("lastChat", chatList.get(0));

            modelMap.addAttribute("chatUser", chatList.get(0).getUserSet().stream()
                    .filter(u -> !u.getId().equals(user.getId())).findFirst().get());

            modelMap.addAttribute("messages", chatList.get(0).getMessageSet());
        }

        modelMap.addAttribute("user", user);
        modelMap.addAttribute("id", user.getId());

        return "chat";
    }

}
