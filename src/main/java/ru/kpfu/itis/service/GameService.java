package ru.kpfu.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.exception.ServerException;
import ru.kpfu.itis.model.entity.Game;
import ru.kpfu.itis.repository.GameRepository;
import ru.kpfu.itis.repository.SpringUserRepository;
import ru.kpfu.itis.utils.HttpClientGame;

import java.util.List;

/**
 * Created by Daniel Shchepetov on 08.12.2016.
 */
@Service
public class GameService {


   private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    public List<Game> getAllGames(){
        List<Game> gameList = null;
        try {
            gameList = new HttpClientGame().getAll();
        } catch (ServerException e) {
            e.printStackTrace();
        }
        gameRepository.save(gameList);
        return gameList;
    }
}
