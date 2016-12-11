package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.model.entity.Group;

/**
 * Created by root on 08.12.16.
 */
public interface SpringGroupRepository extends JpaRepository<Group, Long>{



}
