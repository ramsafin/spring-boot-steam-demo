package ru.kpfu.itis.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.model.entity.Post;

import java.util.Set;

/**
 * Created by root on 14.12.16.
 */
public interface SpringPostRepository extends JpaRepository<Post, Long> {
    Set<Post> findPostByGroupId(Long id);
}
