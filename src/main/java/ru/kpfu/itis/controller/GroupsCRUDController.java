package ru.kpfu.itis.controller;



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

/**
 * Created by root on 08.12.16.
 */
@Controller
public class GroupsCRUDController {

    private final GroupService groupService;

    private final UserService userService;

    public GroupsCRUDController(GroupService groupService, UserService userService){
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
        return "redirect:/group/{id}";
    }


}
