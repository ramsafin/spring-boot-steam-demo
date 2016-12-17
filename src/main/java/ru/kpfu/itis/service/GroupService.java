package ru.kpfu.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.model.entity.Game;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.SpringGroupRepository;

import java.util.List;
import java.util.Set;

@Service
public class GroupService {

    private final SpringGroupRepository groupRepository;

    @Autowired
    public GroupService(SpringGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll(){
        return groupRepository.findAll();
    }

    public Group addGroup(Group group, User owner, String gameId) {
        group.setOwner(owner);
        group.setGame(gameId);
        return groupRepository.save(group);
    }

    public Group findById(Long id) {
        return groupRepository.findOne(id);
    }

    public Set<User> getParticipants(Group group) {
        return groupRepository.findGroupParticipants(group.getId());
    }

    public Set<Group> findUsersGroups(User user){
        return groupRepository.findUsersGroups(user.getId());
    }

    public Set<Group> findByName(String name){
        return groupRepository.findGroupsByName(name);
    }

    public Set<Group> findByGame(String game){
        return groupRepository.findGroupsByGame(game);
    }
}