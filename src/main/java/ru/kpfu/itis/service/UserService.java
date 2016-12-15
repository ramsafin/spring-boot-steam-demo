package ru.kpfu.itis.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.kpfu.itis.exception.ServerException;
import ru.kpfu.itis.model.entity.Game;

import ru.kpfu.itis.model.entity.Chat;
import ru.kpfu.itis.model.entity.Group;

import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.GameRepository;
import ru.kpfu.itis.repository.OpenIdRepository;
import ru.kpfu.itis.repository.SpringUserRepository;
import ru.kpfu.itis.utils.GsonParser;
import ru.kpfu.itis.utils.HttpClientGame;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.List;

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

//    @Scheduled(fixedDelay = 20000)
//    private void synchronizeAllUsers() {
//        List<User> userList = userRepository.findAll();
//        if (!userList.isEmpty()) {
//            for (int i = 0; i < userList.size(); i++) {
//                User user = userList.get(i);
//                List<String> userInfo = getSteamUserInfo(user);
//                user.setSteamNickname(userInfo.get(0));
//                user.setAvatarUrl(userInfo.get(1));
//                List<Long> gameIdList = createUserGameList(user);
//                if (gameIdList != null && !gameIdList.isEmpty()) {
//                    List<Game> gameidList = gameRepository.findAll(gameIdList);
//                    Set gameSet = new HashSet<Game>(gameidList);
//                    user.setGamesSet(gameSet);
//                }
//                userList.set(i, user);
//            }
//            userRepository.save(userList);
//
//        }
//
//    }   THIS IS TOO SLOOOOOOOOOW!!! YEP ^_^ Agreed!

    public User saveUser(User user) {
        //find user's info from steam
        user = findSteamInfo(user);
        return userRepository.save(user);
    }


    //parse json to get nickname and avatar url
    private List<String> getSteamUserInfo(User user) {
        List<String> userInfo = new ArrayList<>();
        String openIdUrl = getOpenId(user);
        try {
            String userURI = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=C0BE2D2257F40B4AE25E21D6B0A48612&steamids=" + openIdUrl;
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

    private String getOpenId(User user) {
        String openIdUrl = openIdRepository.findByUser(user).getOpenidUrl();
        return openIdUrl.substring(36);
    }

    public User updateSteamInfo(User user) {
        user = userRepository.findOne(user.getId());
        user = findSteamInfo(user);
        return userRepository.save(user);

    }

    //method for updating user;
    private User findSteamInfo(User user) {
        List<String> userInfo = getSteamUserInfo(user);
        user.setSteamNickname(userInfo.get(0));
        user.setAvatarUrl(userInfo.get(1));
        List<Long> gameIdList = createUserGameList(user);
        if (gameIdList != null && !gameIdList.isEmpty()) {
            List<Game> gameidList = gameRepository.findAll(gameIdList);
            Set gameSet = new HashSet<Game>(gameidList);
            user.setGamesSet(gameSet);
        }
        return user;
    }

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    public List<Chat> findAllChats(Long userId) {
        return findOne(userId).getChatList();
    }

    public User findUserByToken(String token) {
        return userRepository.findByOpenid(token);
    }

    public User findUserByFullName(String fullName) {
        return userRepository.findByFullName(fullName);
    }

    public void addGroup(Group group, User user){
        user.addGroup(group);
        userRepository.save(user);
    }

    public void unsubscribeFromGroup(Group group, User user){
        user.deleteGroup(group);
        userRepository.save(user);
    }

}
