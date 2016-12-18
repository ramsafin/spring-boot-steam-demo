package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.model.entity.User;

import java.util.Set;

public interface SpringGroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "select u from User u " +
            "inner join u.groupsList g " +
            "where g.id = :groupid")
    Set<User> findGroupParticipants(@Param("groupid") Long groupid);

    @Query(value = "select g from Group g " +
            "inner join g.participantList u " +
            "where u.id = :userid")
    Set<Group> findUsersGroups(@Param("userid") Long id);

    @Query(value = "select * from groups g " +
            "where g.group_name rlike ?1", nativeQuery = true)
    Set<Group> findGroupsByName(@Param("name") String name);

    @Query(value = "select g from Group g " +
            "where g.game = :game")
    Set<Group> findGroupsByGame(@Param("game") String game);
}