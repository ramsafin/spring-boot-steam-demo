package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.model.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
