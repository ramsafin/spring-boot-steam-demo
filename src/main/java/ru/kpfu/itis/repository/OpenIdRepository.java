package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.model.entity.UserOpenIds;

/**
 * Created by Daniel Shchepetov on 09.12.2016.
 */
public interface OpenIdRepository extends JpaRepository<UserOpenIds, String> {
    UserOpenIds findByUser(User user);
}
