package ru.kpfu.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.model.entity.Chat;
import ru.kpfu.itis.service.ChatService;
import ru.kpfu.itis.service.UserService;

import java.util.Arrays;
import java.util.HashSet;

@RestController
public class ChatController {

    private final ChatService chatService;

    private final UserService userService;

    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @RequestMapping("/chat/createChat")
    public ResponseEntity<Chat> createChat(@RequestParam Long id, @RequestParam Long id2) {

        Chat chat = new Chat();

        try {
            chat.setUserSet(new HashSet<>(Arrays.asList(
                    userService.findOne(id),
                    userService.findOne(id2)
            )));

        } catch (Throwable e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(chatService.createChat(chat), HttpStatus.CREATED);
    }


    @RequestMapping("/chat/addMessage/${chatId}")
    public ResponseEntity<Chat> addMessage(@PathVariable Long chatId, @RequestParam("sender") Long senderId) {

    }



}
