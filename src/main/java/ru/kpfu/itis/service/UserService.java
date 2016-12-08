package ru.kpfu.itis.service;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.exception.ServerException;
import ru.kpfu.itis.model.entity.Game;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.SpringUserRepository;

import java.util.List;

@Service
public class UserService {

    private final SpringUserRepository userRepository;

    @Autowired
    public UserService(SpringUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public String getAllGames(){
        String game = null;
        try {
            game = new HttpClientGame().getAll();
        } catch (ServerException e) {
            e.printStackTrace();
        }
        return game;
    }

}
