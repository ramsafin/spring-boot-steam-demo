package ru.kpfu.itis.service;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.exception.ServerException;
import ru.kpfu.itis.model.entity.Game;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.GameRepository;
import ru.kpfu.itis.repository.OpenIdRepository;
import ru.kpfu.itis.repository.SpringUserRepository;
import ru.kpfu.itis.utils.GsonParser;
import ru.kpfu.itis.utils.HttpClientGame;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final SpringUserRepository userRepository;
    private final OpenIdRepository openIdRepository;
    private final GameRepository gameRepository;
    GsonParser parser = new GsonParser();

    @Autowired
    public UserService(SpringUserRepository userRepository, OpenIdRepository openIdRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.openIdRepository = openIdRepository;
        this.gameRepository = gameRepository;
    }

    public User saveUser(User user) {
        List<Long> gameIdList = createUserGameList(user);

        if (gameIdList!=null && !gameIdList.isEmpty()){
            List<Game> gameidList = gameRepository.findAll(gameIdList);
            Set gameSet = new HashSet<Game>(gameidList);
            user.setGamesSet(gameSet);
        }

        return userRepository.save(user);
    }


    private List<Long> createUserGameList(User user) {
        List<Long> gameList = null;
        System.out.println(openIdRepository.findByUser(user));
        String openIdUrl = openIdRepository.findByUser(user).getOpenidUrl();
        openIdUrl = openIdUrl.substring(36);
        System.out.println(openIdUrl);
        try {
            String userURI = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=C0BE2D2257F40B4AE25E21D6B0A48612&steamid=" + openIdUrl + "&format=json";
            String gameString = new HttpClientGame(userURI).getAll();
            gameList = new GsonParser().parseUserGameList(gameString);
        } catch (ServerException e) {
            e.printStackTrace();
        }
        return gameList;
    }
}
