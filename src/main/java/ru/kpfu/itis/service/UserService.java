package ru.kpfu.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.repository.SpringUserRepository;

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

    public User findById(Long id){
        return userRepository.findOne(id);
    }

    public User findByOpenId(OpenIDAuthenticationToken token){
        return userRepository.findByOpenid(token.getName()).get();
    }

    public void addGroup(Group group, User user){
        user.addGroup(group);
        userRepository.save(user);
    }
}