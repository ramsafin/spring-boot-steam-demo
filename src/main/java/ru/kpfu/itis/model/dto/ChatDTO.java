package ru.kpfu.itis.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDateTime;

import java.io.Serializable;
import java.util.List;

public class ChatDTO implements Serializable {

    @JsonProperty("messages")
    private List<MessageDTO> messages;

    @JsonProperty("chatId")
    private Long chatId;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("updated")
    private LocalDateTime updated;

    public ChatDTO(List<MessageDTO> messages, Long chatId, Long userId, String userName, LocalDateTime updated) {
        this.messages = messages;
        this.chatId = chatId;
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

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
