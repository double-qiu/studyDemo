package cn.didadu.sample.thread.cooperate.waxAndBuffer2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car {

	//Lock和Condition对象只有在更加困难的多线程问题中才是需要的
	//此解决方案比前一个更加复杂，在本立中这种复杂性未收获更多，仅示例
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	//true表示已经打蜡
	private boolean waxOn = false;
	
	public void waxed(){
		lock.lock();
		try{
			//打蜡，ready to Buff
			waxOn = true;
			//唤醒抛光线程
			condition.signalAll();
		}finally{
			lock.unlock();
		}
	}
	
	public void buffed(){
		lock.lock();
		
		try{
			//抛光完成，等待下一轮的打蜡
			waxOn = false;
			//唤醒打蜡线程
			condition.signalAll();
		}finally{
			lock.unlock();
		}
	}
	
	public void waitForWaxing() throws InterruptedException{
		lock.lock();
		try{
			while(waxOn == false){
				//false表示已经抛光还没有打蜡，线程挂起，等待打蜡
				condition.await();
			}
		}finally{
			
		}
	}
	
	public void waitForBuffing() throws InterruptedException{
		lock.lock();
		try{
			//true表示已经打蜡，还没有抛光，线程挂起，等待抛光
			while(waxOn == true){
				condition.await();
			}
		}finally{
			lock.unlock();
		}
	}
}
