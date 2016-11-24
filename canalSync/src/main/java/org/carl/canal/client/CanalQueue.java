package org.carl.canal.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import org.carl.canal.redis.queue.RedisQueue;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;

public class CanalQueue extends Thread {
	private RedisQueue<Entry> jedisQueue;
	protected static final ExecutorService threadPool = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	@Override
	public void run() {
		super.run();
		// 开启Canal读取数据线程
		// 一个线程读取数据
		Runnable dfr = new DataFactoryRunnable(jedisQueue);
		new Thread(dfr).start();
	}

	public RedisQueue<Entry> getJedisQueue() {
		return jedisQueue;
	}

	public void setJedisQueue(RedisQueue<Entry> jedisQueue) {
		this.jedisQueue = jedisQueue;
	}

	public CanalQueue(RedisQueue<Entry> jedisQueue) {
		super();
		this.jedisQueue = jedisQueue;
	}
}
