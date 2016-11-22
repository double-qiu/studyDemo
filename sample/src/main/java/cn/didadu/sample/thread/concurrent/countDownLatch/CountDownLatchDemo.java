package cn.didadu.sample.thread.concurrent.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用来同步或多个任务，典型用法是将一个程序分为n个独立的可解决任务
 * @author zj
 *
 */
public class CountDownLatchDemo {

	static final int SIZE = 100;
	
	public static void main(String[] args){
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		CountDownLatch latch = new CountDownLatch(SIZE);
		
		for(int i = 0; i < 10; i++){
			//等待任务中有latch.await()，会阻塞
			exec.execute(new WaitingTask(latch));
		}
		
		for(int i = 0; i < SIZE; i++){
			exec.execute(new TaskPortion(latch));
		}
		
		//等待所有的任务完成
		/*try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//本线程不会阻塞，如果放开上述注释的代码块，调用了latch.await()，则会阻塞，直到100个任务完成
		System.out.println("Latch all tasks");
		
		exec.shutdown();
	}
}
