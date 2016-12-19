package ru.kpfu.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.kpfu.itis.service.UserService;


/**
 * Created by Daniel Shchepetov on 09.12.2016.
 */
@Controller
public class SearchController {

    //TODO

    private final UserService userService;

    @Autowired
    public SearchController(UserService userService) {

        this.userService = userService;
    }


}
