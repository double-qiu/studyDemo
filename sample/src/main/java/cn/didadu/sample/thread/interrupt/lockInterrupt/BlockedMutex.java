package cn.didadu.sample.thread.interrupt.lockInterrupt;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockedMutex {
	private Lock lock = new ReentrantLock();
	
	public BlockedMutex(){
		//始终不释放锁，f()无法执行
		lock.lock();
	}
	
	public void f(){
		try {
			lock.lockInterruptibly();
			System.out.println("lock acquired in f()");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interrupted from lock acuisition in f()");
		}
	}
}
