package ru.kpfu.itis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.model.entity.Chat;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.SpringChatRepository;
import ru.kpfu.itis.repository.SpringMessageRepository;
import ru.kpfu.itis.repository.SpringUserRepository;

import java.util.Arrays;
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



    //find chat between 2 user
    private Chat findChat(final User user, final User user2) {
        List<Chat> chats = chatRepository.findAll(user.getId());
        if (chats == null) return null;
        return chats.stream().filter(chat -> chat.getUserSet()
                .containsAll(Arrays.asList(user, user2))).findFirst().get();
    }

}
