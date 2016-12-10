package ru.kpfu.itis.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDTO {

    @JsonProperty("chatId")
    private Long chatId;

    @JsonProperty("sender")
    private Long sender;

    @JsonProperty("msg")
    private String msg;

    public MessageDTO() {
    }

    public MessageDTO(Long chatId, Long sender, String msg) {
        this.chatId = chatId;
        this.sender = sender;
        this.msg = msg;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
