package ru.kpfu.itis.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MessageDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    private String date;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("chatId")
    private Long chatId;


    public MessageDTO() {
    }

    public MessageDTO(Long id, String date, String sender, String msg, Long chatId, String avatar) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.msg = msg;
        this.avatar = avatar;
        this.chatId = chatId;
    }


    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
