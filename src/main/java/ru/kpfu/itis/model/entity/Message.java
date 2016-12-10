package ru.kpfu.itis.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.inject.internal.Objects;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message")
public class Message implements Serializable {

    private Long id;

    private Chat chat;

    private User sender;

    private String messageText;

    private LocalDateTime sentAt;

    public Message() {
        this.sentAt = LocalDateTime.now();
    }


    public Message(Chat chat, User sender, String messageText) {
        this();
        this.chat = chat;
        this.sender = sender;
        this.messageText = messageText;
    }

    public Message(User sender, String messageText) {
        this(null, sender, messageText);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @JsonIgnore
    @ManyToOne
    public Chat getChat() {
        return chat;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    public User getSender() {
        return sender;
    }

    @Column(name = "message_text")
    public String getMessageText() {
        return messageText;
    }

    @Column(name = "sent_at")
    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (chat != null ? !chat.equals(message.chat) : message.chat != null) return false;
        if (sender != null ? !sender.equals(message.sender) : message.sender != null) return false;
        if (messageText != null ? !messageText.equals(message.messageText) : message.messageText != null) return false;
        return sentAt != null ? sentAt.equals(message.sentAt) : message.sentAt == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(chat, sender, messageText, sentAt);
    }


}
