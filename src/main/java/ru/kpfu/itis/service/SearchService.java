package ru.kpfu.itis.service;

import ru.kpfu.itis.exception.ServerException;
import ru.kpfu.itis.model.entity.Game;
import ru.kpfu.itis.utils.HttpClientGame;

import java.util.List;

/**
 * Created by Daniel Shchepetov on 08.12.2016.
 */
public class SearchService {
    public List<Game> getAllGames(){
        List<Game> gameList = null;
        try {
            gameList = new HttpClientGame().getAll();
        } catch (ServerException e) {
            e.printStackTrace();
        }
        return gameList;
    }
}
