package org.rabbitMQ.demo.controller;

import org.rabbitMQ.demo.domain.User;
import org.rabbitMQ.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qy on 2016/7/23.
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "sendUserMessage")
    public String sendUserMessage(User user) {
        userService.sendUserMessage(user);
        return "操作成功";
    }

    @RequestMapping(value = "createExchageAndBind")
    public String createExchageAndBind() {
        return userService.createExchageAndBind();
    }
}
