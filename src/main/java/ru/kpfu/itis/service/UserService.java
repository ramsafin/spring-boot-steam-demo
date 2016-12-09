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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final SpringUserRepository userRepository;
    private final OpenIdRepository openIdRepository;
    private final GameRepository gameRepository;


    @Autowired
    public UserService(SpringUserRepository userRepository, OpenIdRepository openIdRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.openIdRepository = openIdRepository;
        this.gameRepository = gameRepository;
    }

    public User saveUser(User user) {
        //find user's info from steam
        List<String> userInfo = getSteamUserInfo(user);
        user.setSteamNickname(userInfo.get(0));
        user.setAvatarUrl(userInfo.get(1));
        //find user's games
        List<Long> gameIdList = createUserGameList(user);
        if (gameIdList!=null && !gameIdList.isEmpty()){
            List<Game> gameidList = gameRepository.findAll(gameIdList);
            Set gameSet = new HashSet<Game>(gameidList);
            user.setGamesSet(gameSet);
        }

        return userRepository.save(user);
    }

    //parse json to get nickname and avatar url
    private List<String> getSteamUserInfo(User user){
        List<String> userInfo = new ArrayList<>();
        String openIdUrl = getOpenId(user);
        try {
            String userURI = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=C0BE2D2257F40B4AE25E21D6B0A48612&steamids="+ openIdUrl;
            String gameString = new HttpClientGame(userURI).getAll();
           userInfo = new GsonParser().parseUserInfoList(gameString);

        } catch (ServerException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    private List<Long> createUserGameList(User user) {
        List<Long> gameList = null;
        String openIdUrl = getOpenId(user);
        try {
            String userURI = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=C0BE2D2257F40B4AE25E21D6B0A48612&steamid=" + openIdUrl + "&format=json";
            String gameString = new HttpClientGame(userURI).getAll();
            gameList = new GsonParser().parseUserGameList(gameString);
        } catch (ServerException e) {
            e.printStackTrace();
        }
        return gameList;
    }

    private String getOpenId(User user){
        String openIdUrl = openIdRepository.findByUser(user).getOpenidUrl();
        return openIdUrl.substring(36);
    }
}
