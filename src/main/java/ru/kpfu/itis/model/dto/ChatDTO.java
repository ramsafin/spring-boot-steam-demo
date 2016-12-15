package ru.kpfu.itis.model.dto;

import java.io.Serializable;
import java.util.List;

public class ChatDTO implements Serializable {

    private List<MessageDTO> messages;

    private Long chatId;

    private String meName;

    private Long meId;

    private Long userId;

    private String userName;

    public ChatDTO(List<MessageDTO> messages, Long chatId, String meName, Long meId, Long userId, String userName) {
        this.messages = messages;
        this.chatId = chatId;
        this.meName = meName;
        this.meId = meId;
        this.userId = userId;
        this.userName = userName;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMeName() {
        return meName;
    }

    public void setMeName(String meName) {
        this.meName = meName;
    }

    public Long getMeId() {
        return meId;
    }

    public void setMeId(Long meId) {
        this.meId = meId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
