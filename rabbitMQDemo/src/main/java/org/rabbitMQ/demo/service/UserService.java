package org.rabbitMQ.demo.service;

import org.rabbitMQ.demo.domain.User;

/**
 * Created by qy on 2016/7/23.
 */
public interface UserService {

    void sendUserMessage(User user);

    String createExchageAndBind();
}
