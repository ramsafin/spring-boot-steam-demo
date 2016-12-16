package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.model.entity.User;

import java.util.Set;

public interface SpringGroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "select u from User u inner join fetch u.groupsList g where g.id = :groupId")
    Set<User> findGroupParticipants(@Param("groupId") Long groupId);
}