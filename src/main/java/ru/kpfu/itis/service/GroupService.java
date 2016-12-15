package ru.kpfu.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.SpringGroupRepository;

import java.util.Set;

@Service
public class GroupService {

    private final SpringGroupRepository groupRepository;

    @Autowired
    public GroupService(SpringGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group addGroup(Group group, User owner) {
        group.setOwner(owner);
        return groupRepository.save(group);
    }

    public Group findById(Long id) {
        return groupRepository.findOne(id);
    }

    public Set<User> getPariticipants(Group group) {
        return groupRepository.findGroupParticipants(group.getId());
    }
}