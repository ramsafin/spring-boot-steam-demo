package ru.kpfu.itis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.kpfu.itis.model.handler.CustomAuthenticationSuccessHandler;
import ru.kpfu.itis.service.CustomUserDetailsService;

import javax.servlet.FilterRegistration;

@EnableWebSecurity
public class SecurityWebConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                    .antMatchers("/login/**").permitAll()
                    .antMatchers("/resources/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .openidLogin()
                    .loginPage("/login").permitAll()
                    .authenticationUserDetailsService(authenticationUserDetailsService())
                    .failureUrl("/login?fail")
                    .successHandler(authenticationSuccessHandler())
                .and()
                .csrf().disable();
    }

    @Bean
    public AuthenticationUserDetailsService<OpenIDAuthenticationToken> authenticationUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}
