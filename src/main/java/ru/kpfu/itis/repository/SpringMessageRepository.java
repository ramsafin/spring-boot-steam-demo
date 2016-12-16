package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.model.entity.Message;

import java.util.List;

public interface SpringMessageRepository extends JpaRepository<Message, Long> {

    @Query("select distinct m from Message m inner join fetch m.sender s where m.chat.id = :chatId")
    List<Message> findAllByChatId(@Param("chatId") Long chatId);

    @Query("select distinct count(m) from Message m inner join fetch m.sender s where m.chat.id = :chatId")
    Long countAllByChatId(@Param("chatId") Long chatId);

    @Query("select distinct m from Message " +
            "inner join fetch m.sender s where m.chat.id = :chatId")
    List<Message> findAllNewMessages(@Param("chatId") Long chatId);


}
