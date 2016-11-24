package org.rabbitMQ.demo.config;

import org.rabbitMQ.demo.listener.SpringDemoReciveListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by qy on 2016/7/24.
 */
@Configuration
public class ReceiveConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory receiveConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(this.host);
        factory.setUsername(this.username);
        factory.setPassword(this.password);

        return factory;
    }

    @Bean
    public RabbitTemplate receiveRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(receiveConnectionFactory());
        rabbitTemplate.setQueue("");
        rabbitTemplate.setRoutingKey("");
        return rabbitTemplate;
    }

    /**
     * 声明监听器
     * @return
     */
    @Bean
    public SpringDemoReciveListener springDemoReciveListener() {
        return new SpringDemoReciveListener();
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer() {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(receiveConnectionFactory());
        listenerContainer.addQueueNames("spring-demo-user");
        listenerContainer.setMessageListener(springDemoReciveListener());

        return listenerContainer;
    }
}
