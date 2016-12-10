package ru.kpfu.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.model.entity.Chat;
import ru.kpfu.itis.model.entity.Message;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.ChatRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat createChat(Chat chat) {

        return chatRepository.save(chat);
    }

    public Chat findChat(User user, User user2) {
        Chat chatProbe = new Chat(new HashSet<>(Arrays.asList(user, user2)));
        return chatRepository.findOne(Example.of(chatProbe,
                ExampleMatcher.matching()
                .withIgnorePaths("id", "createdAt", "updatedAt")
        ));
    }

    public void addMessage(Message message, Chat chat) {
        chat.addMessage(message);
        chatRepository.save(chat);
    }

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }


}
