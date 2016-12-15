package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.model.entity.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "select c from Chat c JOIN c.userSet u WHERE u.id = :userId")
    List<Chat> findAllToUser(@Param("userId") Long userId);

}
