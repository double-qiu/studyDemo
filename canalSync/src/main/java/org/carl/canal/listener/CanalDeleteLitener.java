package org.carl.canal.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class CanalDeleteLitener implements MessageListener {

	@Override
	public void onMessage(Message msg) {
		System.out.println("CanalDeleteLitener");
	}

}
