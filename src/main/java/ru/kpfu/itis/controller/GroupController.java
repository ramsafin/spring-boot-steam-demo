package ru.kpfu.itis.controller;

import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.model.entity.Post;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.service.GroupService;
import ru.kpfu.itis.service.PostService;
import ru.kpfu.itis.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

/**
 * Created by root on 14.12.16.
 */
@Controller
public class GroupController {

    private final GroupService groupService;

    private final UserService userService;

    private final PostService postService;

    public GroupController(GroupService groupService, UserService userService, PostService postService){
        this.groupService = groupService;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/group/{id}")
    public String groupPageIndex(@PathVariable Long id, ModelMap map, Principal principal){
        Group group = groupService.findById(id);
        Set<User> participants = groupService.getPariticipants(group);
        Set<Post> posts = postService.getGroupPosts(group);
        if(principal != null){
            User user = userService.findByOpenId((OpenIDAuthenticationToken) principal);
            Set<Group> userGroups = groupService.findUsersGroups(user);
            if(participants.contains(user)){
                map.put("subscribed", true);
            }
            else{
                map.put("subscribed", false);
            }
            if(user.getId().equals(group.getOwner().getId())){
                map.put("isAdmin", true);
                map.put("post", new Post());
            }else{
                map.put("isAdmin", false);
            }
        }
        map.put("group", group);
        map.put("subscribers", participants);
        map.put("posts", posts);
        return "group_main_page";
    }

    @GetMapping("/group/{id}/subscribe")
    public String subscribe(@PathVariable Long id,Principal principal){
        User subscriber = userService.findByOpenId((OpenIDAuthenticationToken) principal);
        Group group = groupService.findById(id);
        userService.addGroup(group,subscriber);
        return "redirect:/group/{id}";
    }

    @GetMapping("/group/{id}/unsubscribe")
    public String unsubscribe(@PathVariable Long id, Principal principal){
        User subscriber = userService.findByOpenId((OpenIDAuthenticationToken) principal);
        Group group = groupService.findById(id);
        userService.unsubscribeFromGroup(group,subscriber);
        return "redirect:/group/{id}";
    }

    @PostMapping("/group/{id}/new_post")
    public String createPost(
            @ModelAttribute("post") @Valid Post post,
            @PathVariable Long id
            ){
        Group group = groupService.findById(id);
        postService.addPost(new Post(post.getTitle(),post.getBody()),group);
        return "redirect:/group/{id}";
    }

}
