package cn.didadu.sample.thread.interrupt.sleepAndIOAndSynchronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;

public class Interrupting {
	
	private static ExecutorService exec = Executors.newCachedThreadPool();

	static void test(Runnable r) throws InterruptedException{
		Future<?> f = exec.submit(r);
		
		TimeUnit.MILLISECONDS.sleep(2000);
		
		System.out.println("Interrupting " + r.getClass().getName());
		f.cancel(true);
		System.out.println("Interrupt sent to " + r.getClass().getName());
	}
	
	public static void main(String[] args) throws InterruptedException{
		
		//可以中断阻塞
		test(new SleepBLocked());
		
		//不可中断阻塞
		test(new IOBlocked(System.in));
		
		//不可中断阻塞
		test(new SynchronizedBlocked());
		
		//等待一会儿，让上面程序跑完
		TimeUnit.SECONDS.sleep(3);
		System.out.println("Aborting with System.exit(0)");
		System.exit(0);
	}
}
 