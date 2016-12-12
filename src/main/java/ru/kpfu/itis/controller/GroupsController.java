package ru.kpfu.itis.controller;



import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.service.GroupService;
import ru.kpfu.itis.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

/**
 * Created by root on 08.12.16.
 */
@Controller
public class GroupsController {

    private final GroupService groupService;

    private final UserService userService;

    public GroupsController(GroupService groupService, UserService userService){
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/new_group")
    public String groupCreatingForm(ModelMap map){
        map.put("group", new Group());
        return "new_group_form";
    }

    @PostMapping("/new_group")
    public String createGroup(
            @ModelAttribute("group") @Valid Group group,BindingResult result,
            Principal principal
    ){
        User owner = userService.findByOpenId((OpenIDAuthenticationToken) principal);
        if(result.hasErrors()){
            return "new_group_form";
        }
        groupService.addGroup(new Group(group.getName(),group.getDescription()), owner);
        return "redirect:/";
    }

    @GetMapping("/group/{id}")
    public String groupPageIndex(@PathVariable Long id,ModelMap map, Principal principal){
        Group group = groupService.findById(id);
        Set<User> participants = groupService.getPariticipants(group);
        if(principal != null){
            User user = userService.findByOpenId((OpenIDAuthenticationToken) principal);
            if(participants.contains(user)){
                map.put("subscribed", true);
            }
            else{
                map.put("subscribed", false);
            }
        }
        map.put("group", group);
        map.put("subscribers", participants);
        return "group_main_page";
    }

    @PostMapping("/group/{id}")
    public String subscribe(@PathVariable Long id,Principal principal){
        User subscriber = userService.findByOpenId((OpenIDAuthenticationToken) principal);
        Group group = groupService.findById(id);
        userService.addGroup(group,subscriber);
        return "redirect:/group/{id}";
    }
}
