package org.carl.canal.client;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.Message;

public class DataFactory extends CanalFactory {
	protected LinkedBlockingQueue<Entry> customTableQueue = new LinkedBlockingQueue<Entry>();

	public static void main(String[] args) {
		DataFactory df = new DataFactory();
		df.getData();
	}

	public void getData() {
		Message message;
		long batchId, size;
		System.err.println("get data from Canal Start...");
		while (true) {
			// message = connector.getWithoutAck(batchSize, timeout,
			// TimeUnit.SECONDS); // 获取指定数量的数据
			message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
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
				for (Entry entry : entryList) {
					try {
						// 加入队列
						printEntry(entry);
						putTask(entry);
					} catch (InterruptedException e) {
						// do nothing
						System.err.println("customTableQueue interrupt.");
						try {
							throw new Exception(e);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			connector.ack(batchId); // 提交确认
			// connector.rollback(batchId); // 处理失败, 回滚数据
		}
	}

	/**
	 * 加入一个队列
	 * 
	 * @throws InterruptedException
	 */
	public void putTask(Entry e) throws InterruptedException {
		customTableQueue.put(e);
	}

	/**
	 * 取出一个消息
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public Entry getTask() throws InterruptedException {
		return customTableQueue.take();
	}
}
