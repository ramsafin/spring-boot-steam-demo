package ru.kpfu.itis.model.entity;

import com.google.inject.internal.Objects;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat")
public class Chat implements Serializable {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<Message> messageSet = new HashSet<>();

    private Set<User> userSet = new HashSet<>();

    public Chat() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Chat(Set<User> userSet) {
        this.userSet = userSet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "created_at")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    @Column(name = "updated_at")
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Message> getMessageSet() {
        return messageSet;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    public Set<User> getUserSet() {
        return userSet;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setMessageSet(Set<Message> messageSet) {
        this.messageSet = messageSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public void addMessage(Message message) {
        message.setChat(this);
        this.messageSet.add(message);
    }

    @Transient
    public Message getLastMessage() {
        if (messageSet.isEmpty()) return null;

        return messageSet.stream().sorted((m1, m2) -> m2.getSentAt()
                .compareTo(m1.getSentAt())).findFirst().get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;
        if (createdAt != null ? !createdAt.equals(chat.createdAt) : chat.createdAt != null) return false;
        return updatedAt != null ? updatedAt.equals(chat.updatedAt) : chat.updatedAt == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(createdAt, updatedAt);
    }
}
