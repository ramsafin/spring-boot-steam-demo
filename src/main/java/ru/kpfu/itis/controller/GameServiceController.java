package ru.kpfu.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kpfu.itis.service.GameService;


/**
 * Created by Daniel Shchepetov on 09.12.2016.
 */
@Controller
public class GameServiceController {



    private final GameService gameService;

    @Autowired
    public GameServiceController(GameService gameService) {

        this.gameService = gameService;
    }

    //update game db
    @ResponseBody
    @GetMapping("/update")
    public String update(){
        gameService.getAllGames();
        return "OK!";
    }
}
