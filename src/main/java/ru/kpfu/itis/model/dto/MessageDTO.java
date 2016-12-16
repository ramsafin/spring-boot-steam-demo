package ru.kpfu.itis.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.kpfu.itis.model.entity.User;

import java.io.Serializable;

public class MessageDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    private String date;

    @JsonProperty("sender")
    private User sender;

    @JsonProperty("message")
    private String message;

    @JsonProperty("chatId")
    private Long chatId;


    public MessageDTO() { }

    public MessageDTO(Long id, String date, User sender, String message) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
