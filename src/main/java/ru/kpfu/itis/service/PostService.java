package ru.kpfu.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.model.entity.Post;
import ru.kpfu.itis.repository.SpringPostRepository;

import java.util.Set;

@Service
public class PostService {

    private SpringPostRepository repository;

    @Autowired
    public PostService(SpringPostRepository repository) {
        this.repository = repository;
    }

    public Post addPost(Post post, Group group) {
        post.setGroup(group);
        return repository.save(post);
    }

    public Set<Post> getGroupPosts(Group group) {
        return repository.findPostByGroupId(group.getId());
    }
}
