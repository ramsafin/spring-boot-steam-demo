package ru.kpfu.itis.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.model.entity.Post;

import java.util.Set;

public interface SpringPostRepository extends JpaRepository<Post, Long> {

    Set<Post> findPostByGroupId(Long id);

}
