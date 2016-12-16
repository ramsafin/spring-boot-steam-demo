package ru.kpfu.itis.repository;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.model.entity.Chat;

import java.util.List;

public interface SpringChatRepository extends JpaRepository<Chat, Long> {


    //get count of chats which are updated after fromDate
    @Query("select distinct count(c) from Chat c where c.updatedAt >= :fromDate")
    Long countAllChatsFromDate(@Param("fromDate") LocalDateTime fromDate);

    //get all chats which are updated after fromDate
    @Query("select distinct c from Chat c join fetch c.messageSet " +
            "join fetch c.userSet us where c.updatedAt >= :fromDate")
    List<Chat> findAllChatFromDate(@Param("fromDate") LocalDateTime fromDate);


    /**
     * SELECT all chats where participating user with userId, fetching users, messages
     *
     * @param userId - user id
     * @return chats
     */
    @Query("select distinct c from Chat c left join fetch c.messageSet ms " +
            "left join fetch c.userSet us where ms.sender.id = :userId")
    List<Chat> findAll(@Param("userId") Long userId);


    /**
     * SELECT all chats without messages
     *
     * @param userId - user id
     * @return chats (without messages), see findAll(Long userId)
     */
    @Query("select distinct c from Chat c inner join fetch c.userSet u")
    List<Chat> findAllWithoutFetchMessages(@Param("userId") Long userId);




}
