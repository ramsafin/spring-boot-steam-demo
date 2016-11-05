package ru.kpfu.itis;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;

public class CustomUserDetailsService implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
        return new User(token.getName(), "", AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
}
