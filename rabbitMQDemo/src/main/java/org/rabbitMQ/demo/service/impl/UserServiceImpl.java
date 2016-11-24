package org.rabbitMQ.demo.service.impl;

import org.rabbitMQ.demo.domain.User;
import org.rabbitMQ.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qy on 2016/7/23.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String EXCHAGE = "spring-demo";
    private static final String ROUTINGKEY = "spring-*-user";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Override
    public void sendUserMessage(User user) {
        try {
            rabbitTemplate.convertAndSend(EXCHAGE, ROUTINGKEY, user);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String createExchageAndBind() {
        try {
            Exchange exchange = new TopicExchange(EXCHAGE);
            amqpAdmin.declareExchange(exchange);
            Queue queue = new Queue("spring-demo-user");
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY).noargs());
            return "绑定成功";
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return "绑定失败";
    }
}
