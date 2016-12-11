package ru.kpfu.itis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.service.GroupService;
import ru.kpfu.itis.service.UserService;

/**
 * Created by root on 08.12.16.
 */
@Controller
public class GroupsController {

    private final GroupService groupService;


    public GroupsController(GroupService groupService){
        this.groupService = groupService;
    }

    @GetMapping("/new_group")
    public String groupCreatingForm(ModelMap map){
        map.put("group", new Group());
        return "new_group_form";
    }

}
