package org.carl.canal.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.carl.canal.redis.queue.RedisQueue;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.Message;

public class DataFactoryRunnable extends CanalFactory implements Runnable {
	private RedisQueue<Entry> redisQueue;
	Message message;
	long batchId, size;

	public DataFactoryRunnable(RedisQueue<Entry> redisQueue) {
		super();
		this.redisQueue = redisQueue;
		// 初始化Canal连接信息
		initCanalConnector();
	}

	public void run() {
		L.info("get data from Canal Start...");
//		while (!threadPool.isShutdown() && !threadPool.isTerminated()) {
		while (true) {
			message = connector.getWithoutAck(batchSize, timeout, TimeUnit.SECONDS); // 获取指定数量的数据
			// message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
			batchId = message.getId();
			size = message.getEntries().size();
			if (batchId == -1 || size == 0) {
				try {
					Thread.sleep(1000);
					System.err.println("empty!");
				} catch (InterruptedException e) {
					System.err.println("CustomSimpleCanalClient invoke thread interrupted!");
				}
			} else {
				System.err.println("Get " + size + " data's from Canal...");
				List<Entry> entryList = message.getEntries();
				// 开启Redis连接
				for (Entry entry : entryList) {
					try {
						// 加入队列
						// printEntry(entry);
						// redisQueue.put(entry);
						redisQueue.pushFromHead(entry);
					} catch (Exception e) {
						// do nothing
						System.err.println("customTableQueue interrupt.");
						e.printStackTrace();
					}
				}
				// 通知确认,下次不再取该条数据
				connector.ack(batchId); // 提交确认
				// 提交确认后需要读取该数据
				// connector.rollback(batchId); // 处理失败, 回滚数据
			}
			// redisQueue.close();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		redisQueue.destroy();
	}

}
