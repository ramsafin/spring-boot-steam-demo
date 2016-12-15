package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.model.entity.Game;




/**
 * Created by Daniel Shchepetov on 08.12.2016.
 */
public interface GameRepository extends JpaRepository<Game, Long> {

}
