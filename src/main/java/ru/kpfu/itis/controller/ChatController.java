package ru.kpfu.itis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.model.dto.MessageDTO;
import ru.kpfu.itis.model.entity.Chat;
import ru.kpfu.itis.model.entity.Message;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.service.ChatService;
import ru.kpfu.itis.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RestController
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;

    private final UserService userService;

    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }



    @GetMapping("/chat/createChat")
    public ResponseEntity<Chat> createChat(@RequestParam Long userId, @RequestParam Long userId2) {

        Chat chat = new Chat();

        try {
            chat.setUserSet(new HashSet<>(Arrays.asList(
                    userService.findOne(userId),
                    userService.findOne(userId2)
            )));

        } catch (Throwable e) {
            log.error("[Error in 'createChat' method]", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(chatService.createChat(chat), HttpStatus.CREATED);
    }


    @GetMapping("/chat/{chatId}")
    public ResponseEntity<Chat> findChat(@PathVariable("chatId") Long chatId) {

        log.error(String.format("Chat id : %d", chatId));

        try {
            return new ResponseEntity<>(chatService.findChat(chatId), HttpStatus.FOUND);

        } catch (Throwable e) {
            log.error("[Error in 'findChat' method]", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/chat/addMessage")
    public ResponseEntity<Chat> addMessage(@RequestBody MessageDTO msg) {

        log.error(String.format("Message : chat id = %d, sender : %d", msg.getChatId(), msg.getSender()));

        //TODO check if user restricted
        try {

            Chat chat = chatService.findChat(msg.getChatId());
            User user = userService.findOne(msg.getSender());

            Message message = new Message(chat, user, msg.getMsg());

            return new ResponseEntity<>(chatService.addMessage(message, chat), HttpStatus.ACCEPTED);

        } catch (Throwable e) {
            log.error("[Error in 'addMessage' method]", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/chat/chats/{userId}")
    public ResponseEntity<List<Chat>> findAllChats(@PathVariable("userId") Long userId) {

        log.error(String.format("User id : %d", userId));

        try {

            return new ResponseEntity<>(chatService.findAll(userId), HttpStatus.FOUND);

        } catch (Throwable e) {
            log.error("[Error in 'findAllChats' method]", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/chat/find")
    public ResponseEntity<Chat> findChat(@RequestParam Long userId1, Long userId2) {

        log.error(String.format("user 1 : %d, user 2 : %d", userId1, userId2));

        try {

            User user1 = userService.findOne(userId1);

            User user2 = userService.findOne(userId2);

            return new ResponseEntity<>(chatService.findChat(user1, user2) ,HttpStatus.OK);

        } catch (Throwable e) {
            log.error("[Error in 'findChat for 2 users' method]", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
