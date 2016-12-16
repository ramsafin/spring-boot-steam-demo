package ru.kpfu.itis.controller;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.model.entity.Chat;
import ru.kpfu.itis.model.entity.Message;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.SpringChatRepository;
import ru.kpfu.itis.repository.SpringMessageRepository;
import ru.kpfu.itis.repository.SpringUserRepository;

import java.util.Collections;
import java.util.List;

@RestController
public class ChatApiController {

    private static final Logger log = LoggerFactory.getLogger(ChatApiController.class);

    private final SpringChatRepository chatRepository;
    private final SpringUserRepository userRepository;
    private final SpringMessageRepository messageRepository;

    @Autowired
    public ChatApiController(
            SpringChatRepository chatRepository,
            SpringUserRepository userRepository,
            SpringMessageRepository messageRepository
    ) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }


    @GetMapping(value = "/api/chat/messages")
    public List<Message> findAllMessages(@RequestParam("id") Long id) {

        log.error("[select messages]");

        List<Message> messages = messageRepository.findAllByChatId(id);

        log.error("[messages was selected]");

        if (messages == null || messages.isEmpty()) {
            log.error("[empty messages or null]");
            return Collections.emptyList();
        }

        log.error("selecting user");

        User user = messages.get(0).getSender();

        log.error(String.valueOf(user.getId()));

        log.error("user selected");

        log.error("selecting chat");

        Chat chat = messages.get(0).getChat();

        log.error(String.valueOf(chat.getId()));

        log.error("chat selected");

        return messages;
    }

    @GetMapping(value = "/api/chat/find")
    public Long findAll() {

        log.error("[selecting new chats]");

        Long count = chatRepository.countAllChatsFromDate(LocalDateTime.now().minusHours(1));

        if (count == null) {
            log.error("[null]");
        }

        return count;
    }


    @GetMapping(value = "/api/user/find")
    public User findUser(@RequestParam("name") String fullName) {

        User user = userRepository.findOne(1L);

        log.error("[user was selected]");
        List<Chat> chatList = user.getChatList();

        if (chatList == null) {
            log.error("[chatList is null]");
        } else {
            log.error(String.format("ChatList size : %d", chatList.size()));
        }

        log.error("[user's getOpenIdsSet was invoked]");
        return user;
    }

}
