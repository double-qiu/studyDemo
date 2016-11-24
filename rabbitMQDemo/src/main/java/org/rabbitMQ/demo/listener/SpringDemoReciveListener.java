package org.rabbitMQ.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by qy on 2016/7/24.
 */
public class SpringDemoReciveListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringDemoReciveListener.class);

    @Override
    public void onMessage(Message message) {
        LOGGER.info(new String(message.getBody()));
    }
}
