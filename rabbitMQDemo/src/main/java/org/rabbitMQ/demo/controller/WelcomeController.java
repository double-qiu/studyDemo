package org.rabbitMQ.demo.controller;

import org.rabbitMQ.demo.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by qy on 2016/7/23.
 */
@RestController
public class WelcomeController {

    @RequestMapping(value = "welcome")
    public ResponseEntity<User> welcome() {
        User user = new User("hehe", 18, new Date());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
