package org.carl.canal.client;

import java.util.logging.Level;

import org.carl.canal.redis.queue.RedisQueue;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;

public class TaskQueue extends CanalFactory implements Runnable{
	private RedisQueue<Entry> jedisQueue;

	public RedisQueue<Entry> getJedisQueue() {
		return jedisQueue;
	}

	public void setJedisQueue(RedisQueue<Entry> jedisQueue) {
		this.jedisQueue = jedisQueue;
	}

	public TaskQueue(RedisQueue<Entry> jedisQueue) {
		super();
		this.jedisQueue = jedisQueue;
	}

	@Override
	public void run() {
		startTask();
	}

	public void startTask() {
		initCanalConnector();
		Object value = null;
		try {
			while (true) {
				value = jedisQueue.takeFromHead();
				L.log(Level.WARNING, value.toString());
				parseRowData2RabbitMQ((Entry) value);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
