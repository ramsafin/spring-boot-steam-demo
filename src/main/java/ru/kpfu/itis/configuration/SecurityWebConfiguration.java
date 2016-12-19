package ru.kpfu.itis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import ru.kpfu.itis.service.CustomUserDetailsService;

@EnableWebSecurity
public class SecurityWebConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/chat/**", "/dialogs/**", "/group/**", "/groups/**").permitAll()
                .antMatchers("/js/**", "/img/**", "/fonts/**","/css/**", "/bower_components/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/resources/**", "/webjars/**", "/built/**", "/static/**").permitAll()
                .and()
                .openidLogin()
                .loginPage("/").permitAll()
                .authenticationUserDetailsService(authenticationUserDetailsService())
                .failureUrl("/?fail")
                .and()
                .csrf().disable();
    }

    @Bean
    public AuthenticationUserDetailsService<OpenIDAuthenticationToken> authenticationUserDetailsService() {
        return new CustomUserDetailsService();
    }

}
