package ru.kpfu.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kpfu.itis.repository.SpringUserRepository;

@Controller
public class SearchController {

    private final SpringUserRepository userRepository;

    @Autowired
    public SearchController(SpringUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/search/users")
    public String showSearch() {
        //TODO search users by criteria
        return "search";
    }

    @ResponseBody
    @GetMapping("/search/users/criteria")
    public ResponseEntity<Object> search(@RequestParam("name") String name) {

        //TODO
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
