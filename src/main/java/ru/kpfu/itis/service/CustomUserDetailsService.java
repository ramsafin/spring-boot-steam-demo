package ru.kpfu.itis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.model.entity.UserOpenIds;
import ru.kpfu.itis.repository.SpringUserRepository;

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
            return springUserRepository.findByOpenid(token.getName());

        } catch (RuntimeException e) {

            ru.kpfu.itis.model.entity.User u = new ru.kpfu.itis.model.entity.User();
            UserOpenIds openIds = new UserOpenIds();
            openIds.setOpenidUrl(token.getName());
            u.addOpenId(openIds);
            openIds.setUser(u);

            return springUserRepository.save(u);
        }
    }
}
