package org.carl.canal.test;

import org.carl.canal.client.CanalQueue;
import org.carl.canal.client.TaskQueue;
import org.carl.canal.redis.queue.RedisQueue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;

public class Main {
	private RedisQueue<Entry> jedisQueue;

	{
		BeanFactory ac = new ClassPathXmlApplicationContext("classpath*:/spring-redis.xml");
		jedisQueue = (RedisQueue) ac.getBean("jedisQueue");
	}

	{
		CanalQueue cq = new CanalQueue(jedisQueue);
		cq.start();
//		for (int i = 0; i < taskSize; i++) {
//			threadPool.execute(new TaskQueue(jedisQueue));
//		}
//		threadPool.shutdown();
//		try {
//			jedisQueue.destroy();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		 Thread tq = new Thread(new TaskQueue(jedisQueue));
		 tq.start();
	}

	public static void main(String[] args) throws InterruptedException {
		Main m = new Main();
	}
}
