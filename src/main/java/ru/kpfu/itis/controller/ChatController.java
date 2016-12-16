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


    @GetMapping("/dialogs/user/{id}")
    public String chat(@PathVariable("id") Long id, Principal principal, ModelMap modelMap) {

        log.error(String.format("Requested url /dialogs/user/%d", id));

        log.error("[openid = " + principal.getName() + "]");

        User user;

        //TODO if principal is null then get user with id = 2
        if (principal == null) {
            log.error("[principal is null]");
            user = userRepository.findUserById(2L); //delete this in production

        } else {
            user = userRepository.findUserByOpenid(principal.getName());
            log.error("[user with id = " + user.getId() + "]");
        }

        if (!id.equals(user.getId())) {
            log.error("id : + " + id + ", id user : " + user.getId());
            return "404"; //TODO check 404
        }

        List<Chat> chatList = user.getChatList(); //fetch chats

        modelMap.addAttribute("chats", chatList);

        if (chatList.size() > 0) {


            modelMap.addAttribute("lastChat", chatList.get(0));

            modelMap.addAttribute("chatUser", chatList.get(0).getUserSet().stream()
                    .filter(u -> !u.getId().equals(id)).findFirst().get());

            modelMap.addAttribute("messages", chatList.get(0).getMessageSet());
        }

        modelMap.addAttribute("user", user);
        modelMap.addAttribute("id", id);

        return "test/chat";
    }

}
