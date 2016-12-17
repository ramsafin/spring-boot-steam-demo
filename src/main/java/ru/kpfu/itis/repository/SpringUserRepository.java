package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.model.entity.User;

public interface SpringUserRepository extends JpaRepository<User, Long> {

    //select user join openid with some openid
    @Query("select distinct users from User users join fetch users.userOpenIdsSet o " +
            "where o.openidUrl = :openid")
    User findByOpenid(@Param("openid") String openid);


    //select user join openid with some fullName
    @Query("select distinct users from User users inner join fetch users.userOpenIdsSet o " +
            "where users.fullName = :fullName")
    User findByFullName(@Param("fullName") String fullName);

//======================================================================================================================

    //select user join openid join chat by fullName
    @Query("select distinct us from User us inner join fetch us.userOpenIdsSet os " +
            "inner join fetch us.chatList cl where us.fullName = :fullName")
    User findUserByFullName(@Param("fullName") String fullName);


    //select user join openid join chat by openid
    @Query("select distinct us from User us join fetch us.userOpenIdsSet os " +
            "inner join fetch us.chatList cl where os.openidUrl = :openid")
    User findUserByOpenid(@Param("openid") String openid);


    @Query("select distinct us from User us left join fetch us.userOpenIdsSet os " +
            "left join fetch us.chatList cl where us.id = :id")
    User findUserById(@Param("id") Long id);
}
