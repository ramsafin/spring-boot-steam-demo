package ru.kpfu.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.model.entity.Chat;
import ru.kpfu.itis.model.entity.Message;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.ChatRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    private final UserService userService;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    public Chat createChat(Chat chat) {

        return chatRepository.save(chat);
    }

    public Chat findChat(Long id) {
        Chat chat = chatRepository.findOne(id);
        chat.setMessageList(chat.getMessageList().stream().distinct().collect(Collectors.toList()));
        return chat;
    }

    public Chat findChat(final User user, final User user2) {
        List<Chat> chats = userService.findAllChats(user.getId());
        if (chats == null) return null;
        return chats.stream().filter(chat -> chat.getUserSet()
                .containsAll(Arrays.asList(user, user2))).findFirst().get();
    }

    public Chat addMessage(Message message, Chat chat) {
        chat.addMessage(message);
        return chatRepository.save(chat);
    }

    public List<Chat> findAll(Long userId) {
        return userService.findAllChats(userId);
    }

}
