package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.model.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {


}
