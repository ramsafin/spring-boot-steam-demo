package ru.kpfu.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.model.entity.Post;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.service.GroupService;
import ru.kpfu.itis.service.PostService;
import ru.kpfu.itis.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class GroupController {

    private final GroupService groupService;

    private final UserService userService;

    private final PostService postService;

    @Autowired
    public GroupController(GroupService groupService, UserService userService, PostService postService) {
        this.groupService = groupService;
        this.userService = userService;
        this.postService = postService;
    }


    @GetMapping("/group/{id}")
    public String groupPageIndex(@PathVariable Long id, ModelMap map, Principal principal) {
        Group group = groupService.findById(id);
        Set<User> participants = groupService.getParticipants(group);
        Set<Post> posts = postService.getGroupPosts(group);
        if (principal != null) {
            User user = userService.findUserByToken(principal.getName());
            Set<Group> userGroups = groupService.findUsersGroups(user);
            if (participants.contains(user)) {
                map.put("subscribed", true);
            } else {
                map.put("subscribed", false);
            }
            if (user.getId().equals(group.getOwner().getId())) {
                map.put("isAdmin", true);
                map.put("post", new Post());
            } else {
                map.put("isAdmin", false);
            }
        }
        map.put("group", group);
        map.put("subscribers", participants);
        map.put("posts", posts);
        return "group_main_page";
    }



    @GetMapping("/group/{id}/subscribe")
    public String subscribe(@PathVariable Long id, Principal principal) {

        User subscriber = userService.findUserByToken(principal.getName());

        Group group = groupService.findById(id);

        userService.addGroup(group, subscriber);

        return "redirect:/group/{id}";
    }

    @GetMapping("/group/{id}/unsubscribe")
    public String unsubscribe(@PathVariable Long id, Principal principal) {

        User subscriber = userService.findUserByToken(principal.getName());

        Group group = groupService.findById(id);

        userService.unsubscribeFromGroup(group, subscriber);

        return "redirect:/group/{id}";
    }

    @PostMapping("/group/{id}/new_post")
    public String createPost(
            @ModelAttribute("post") @Valid Post post,
            @PathVariable Long id
    ) {
        Group group = groupService.findById(id);
        postService.addPost(new Post(post.getTitle(), post.getBody()), group);
        return "redirect:/group/{id}";
    }

    @GetMapping("/groups")
    public String groupsList(ModelMap map){
        List<Group> groups = groupService.findAll();
        map.put("groups", groups);
        return "test/groups";
    }

    @PostMapping("/groups")
    public String groupsListPost(@RequestParam("search") String searchParam,
                                 @RequestParam("sort") String sortParam,
                                 @RequestParam("criteria") String criteria,
                                 ModelMap map){
        List<Group> groups = new ArrayList<>();
        switch (searchParam){
            case "name":
                groups.addAll(groupService.findByName(criteria));
                break;
            case "game":
                groups.addAll(groupService.findByGame(criteria));
                break;
        }
        switch (sortParam){
            case "date":
                Collections.sort(groups, new Comparator<Group>(){
                    public int compare(Group group1, Group group2){
                        return group1.getCreatedTime().compareTo(group2.getCreatedTime());
                    }
                });
                break;
            case "a":
                Collections.sort(groups, new Comparator<Group>(){
                    public int compare(Group group1, Group group2){
                        return group1.getName().compareTo(group2.getName());
                    }
                });
                break;
            case "popular":
                Collections.sort(groups, new Comparator<Group>(){
                    public int compare(Group group1, Group group2){
                        if(group1.getParticipantList().size() == group2.getParticipantList().size()){
                            return 0;
                        }else {
                            if (group1.getParticipantList().size() > group2.getParticipantList().size()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    }
                });

        }
        map.put("groups", groups);
        return "test/groups";
    }

}
