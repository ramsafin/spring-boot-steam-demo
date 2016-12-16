package ru.kpfu.itis.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;

@Entity
@Table(name = "message")
public class Message implements Serializable {

    private Long id;

    private Chat chat;

    private User sender;

    private String messageText;

    private LocalDateTime sentAt;

    private boolean isNew = false;

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
    @ManyToOne(fetch = FetchType.LAZY)
    public Chat getChat() {
        return chat;
    }

    @JsonProperty("sender")
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    public User getSender() {
        return sender;
    }

    @JsonProperty("message")
    @Column(name = "message_text")
    public String getMessageText() {
        return messageText;
    }

    @JsonProperty("sent")
    @Column(name = "sent_at")
    public LocalDateTime getSentAt() {
        return sentAt;
    }

    @JsonProperty("new")
    @Column(name = "is_new")
    public boolean getIsNew() {
        return isNew;
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

    public void setIsNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != null ? !id.equals(message.id) : message.id != null) return false;
        if (chat != null ? !chat.equals(message.chat) : message.chat != null) return false;
        if (sender != null ? !sender.equals(message.sender) : message.sender != null) return false;
        if (messageText != null ? !messageText.equals(message.messageText) : message.messageText != null) return false;
        return sentAt != null ? sentAt.equals(message.sentAt) : message.sentAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (chat != null ? chat.hashCode() : 0);
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (messageText != null ? messageText.hashCode() : 0);
        result = 31 * result + (sentAt != null ? sentAt.hashCode() : 0);
        return result;
    }

    public static Comparator<Message> compareByDates() {
        return (m1, m2) -> {
            if (m1 == null && m2 == null) return 0;
            if (m1 == null) return -1;
            if (m2 == null) return 1;
            return m1.getSentAt().compareTo(m2.getSentAt());
        };
    }

}
