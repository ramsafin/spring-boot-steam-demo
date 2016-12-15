package ru.kpfu.itis.controller;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.model.dto.ChatDTO;
import ru.kpfu.itis.model.dto.MessageDTO;
import ru.kpfu.itis.model.entity.Chat;
import ru.kpfu.itis.model.entity.Message;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.service.ChatService;
import ru.kpfu.itis.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChatAPIController {

    private static final Logger log = LoggerFactory.getLogger(ChatAPIController.class);

    private ChatService chatService;

    private UserService userService;

    @Lazy
    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    @Lazy
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/chat/createChat")
    public ResponseEntity<Chat> createChat(@RequestParam Long userId, Principal principal) {

        Chat chat = new Chat();

        User user;

        if (principal == null) {
            user = userService.findOne(2L); //TODO delete this
        } else {
            user = userService.findUserByToken(principal.getName());
        }

        try {
            chat.setUserSet(new HashSet<>(Arrays.asList(
                    userService.findOne(userId),
                    user
            )));

        } catch (Throwable e) {
            log.error("[Error in 'createChat' method]", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(chatService.createChat(chat), HttpStatus.CREATED);
    }


    //get Chat with some id
    @GetMapping("/api/chat/{chatId}")
    public ResponseEntity<ChatDTO> findChat(
            @PathVariable("chatId") Long chatId,
            @CookieValue(name = "user", required = false) String me
    ) {

        log.error(String.format("Chat id : %d, me : %s", chatId, me));

        try {

            Chat chat = chatService.findChat(chatId);

            if (chat != null) {

                User user = (me != null) ? userService.findUserByToken(me) : userService.findOne(2L);

                log.error("[User id is " + user.getId() + "]");

                User chatUser = chat.getUserSet().stream().filter(u -> !u.getId().equals(user.getId())).findFirst().get();

                log.error("[Chat user id is " + chatUser.getId() + "]");

                ChatDTO chatDTO = new ChatDTO(
                        chat.getMessageSet().stream()
                                .sorted(Comparator.comparing(Message::getSentAt))
                                .map(message -> new MessageDTO(message.getId(), toStringDate(message.getSentAt()),
                                        message.getSender().getFullName(), message.getMessageText(), chatId, "avatar"))
                                .collect(Collectors.toList()),
                        chatId,
                        chatUser.getId(),
                        chatUser.getFullName()
                );

                return new ResponseEntity<>(chatDTO, HttpStatus.FOUND);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Throwable e) {
            log.error("[Error in 'findChat' method]", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //add Message to chat
    @PostMapping(value = "/api/chat/addMessage")
    public ResponseEntity<MessageDTO> addMessage(@RequestBody MessageDTO msg) {

        log.error(String.format("Message : chat id = %d, sender : %s", msg.getChatId(), msg.getSender()));

        try {

            Chat chat = chatService.findChat(msg.getChatId());
            User user = userService.findUserByFullName(msg.getSender());

            if (chat != null && user != null) {

                log.error("[User id is " + user.getId() + "]");

                Message message = new Message(chat, user, msg.getMsg());

                message = chatService.addMessage(message); //save message

                MessageDTO messageDTO = new MessageDTO(
                        message.getId(),
                        toStringDate(message.getSentAt()),
                        msg.getSender(),
                        msg.getMsg(),
                        msg.getChatId(),
                        "avatar"
                );

                return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Throwable e) {
            log.error("[Error in 'addMessage' method]", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get all user's chats
    @GetMapping(value = "/api/chat/chats")
    public ResponseEntity<List<Chat>> findAllChats(Principal principal) {

        User user;

        if (principal == null) {
            log.error("principal is null");
            user = userService.findOne(2L); //TODO delete this
        } else {
            log.error("principal " + principal.getName());
            user = userService.findUserByToken(principal.getName());
        }

        log.error(String.format("User id : %d", user.getId()));

        try {
            List<Chat> chatList = chatService.findAll(user.getId());

            if (chatList != null && !chatList.isEmpty())
                return new ResponseEntity<>(chatService.findAll(user.getId()), HttpStatus.FOUND);

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Throwable e) {
            log.error("[Error in 'findAllChats' method]", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //find chat between two users, me and other
    @GetMapping(value = "/api/chat/find")
    public ResponseEntity<Chat> findChatBetween(@RequestParam Long userId, Principal principal) {

        User user;

        if (principal == null) {
            log.error("principal is null");
            user = userService.findOne(2L); //TODO delete this
        } else {
            log.error("principal " + principal.getName());
            user = userService.findUserByToken(principal.getName());
        }

        log.error(String.format("user other : %d", userId));

        try {

            User userOther = userService.findOne(userId);

            if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Chat chat = chatService.findChat(user, userOther);

            if (chat != null)
                return new ResponseEntity<>(chatService.findChat(user, userOther), HttpStatus.OK);

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Throwable e) {
            log.error("[Error in 'findChat for 2 users' method]", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/api/chat/getChatId")
    public String test(HttpServletRequest req) {
        String url = req.getHeader("referer");
        return url.substring(url.lastIndexOf('/') + 1, url.length());
    }

    private static String toStringDate(LocalDateTime dateTime) {
        //TODO hours:minutes:seconds
        return dateTime.toString("MM-dd-yyyy HH:mm:ss");
    }

}
