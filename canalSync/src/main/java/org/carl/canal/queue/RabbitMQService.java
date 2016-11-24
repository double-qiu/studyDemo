package org.carl.canal.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("resource")
public class RabbitMQService {
	private static MessageConverter messageConverter = new SimpleMessageConverter();
	private static RabbitTemplate rabbitTempalte;

	static{
		if (null == rabbitTempalte) {
			ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[] { "spring-*.xml" });
			rabbitTempalte = (RabbitTemplate) cxt.getBean("rabbitTemplate");
		}
	}

	public static void save2RabbitMQ(RabbitMQType rabbitMQType, String message) {
		rabbitTempalte.send(rabbitMQType.exchange(), rabbitMQType.routingKey(),
				messageConverter.toMessage(message, null));
	}


	public Object getData4Queue(RabbitMQType rabbitMQType) {
		return rabbitTempalte.receiveAndConvert(rabbitMQType.routingKey());
	}

	public static void main(String[] args) {
		RabbitMQService rs = new RabbitMQService();
		System.out.println(rs.getData4Queue(RabbitMQType.INSERT));
	}
}
