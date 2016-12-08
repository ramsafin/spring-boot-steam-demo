package ru.kpfu.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.exception.ServerException;
import ru.kpfu.itis.model.entity.Game;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.SpringUserRepository;
import ru.kpfu.itis.utils.GsonParser;
import ru.kpfu.itis.utils.HttpClientGame;

import java.util.List;

@Service
public class UserService {
    private final SpringUserRepository userRepository;
GsonParser parser = new GsonParser();
    @Autowired
    public UserService(SpringUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<Long> getGames(){
        List<Long> gameList = null;

        try {
            String userURI = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=C0BE2D2257F40B4AE25E21D6B0A48612&steamid=76561198121718567&format=json";
           String gameString = new HttpClientGame(userURI).getAll();
            gameList = new GsonParser().parseUserGameList(gameString);
        } catch (ServerException e) {
            e.printStackTrace();
        }
        return gameList;
    }
}
