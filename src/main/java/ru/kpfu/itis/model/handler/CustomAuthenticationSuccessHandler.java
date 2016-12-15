package ru.kpfu.itis.model.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.kpfu.itis.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //get user
        User user = (User) authentication.getPrincipal();

        if (user == null) {
            log.error("[User is null)]");
            response.sendRedirect("/login?fail");
            return;
        }

        //check if user is new
        if (user.getFullName() == null) {
            log.error("[redirect to '/login/continue/{id}']");
            //new user, redirect him to new form
            response.sendRedirect(String.format("/login/continue/%d", user.getId()));
            return;
        }

        String refer = request.getHeader("referer");

        if (refer != null) {
            response.sendRedirect(refer);
        }

        response.sendRedirect("/");

    }
}
