package ru.kpfu.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.SpringGroupRepository;

/**
 * Created by root on 08.12.16.
 */
@Service
public class GroupService {

    private final SpringGroupRepository groupRepository;

    @Autowired
    public GroupService(SpringGroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public Group addGroup(Group group, User user){
        group.setOwner(user);
        return groupRepository.save(group);
    }
}
