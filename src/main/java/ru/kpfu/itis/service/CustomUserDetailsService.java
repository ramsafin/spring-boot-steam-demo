package ru.kpfu.itis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.model.entity.User;
import ru.kpfu.itis.model.entity.UserOpenIds;
import ru.kpfu.itis.repository.SpringUserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private SpringUserRepository springUserRepository;

    @Autowired
    public void setSpringUserRepository(SpringUserRepository springUserRepository) {
        this.springUserRepository = springUserRepository;
    }

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {

        log.error("Loading user details ...");

        try {
            log.error("Finding user by token " + token.getName());

            Optional<User> user = springUserRepository.findByOpenid(token.getName());

            if (user.isPresent()) return user.get();

            throw new UsernameNotFoundException("User is no found");

        } catch (RuntimeException e) {

            log.error("Exception caught", e);

            User u = new User();
            UserOpenIds openIds = new UserOpenIds(token.getName());
            u.addOpenId(openIds);

            log.error("Saving user and return ...");
            return springUserRepository.save(u);
        }

    }
}
