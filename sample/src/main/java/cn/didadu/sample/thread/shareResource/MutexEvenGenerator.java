package cn.didadu.sample.thread.shareResource;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEvenGenerator extends IntGenerator{

	private int currentEvenValue = 0;
	
	private Lock lock = new ReentrantLock();
	
	@Override
	public int next() {
		// TODO Auto-generated method stub
		lock.lock();
		
		try{
			++currentEvenValue;
			Thread.yield();
			++currentEvenValue;
			return currentEvenValue;
		}finally{
			lock.unlock();
		}
	}
	
	public static void main(String[] args){
		//同步了next()方法中的代码块，共享资源的访问不会出问题
		EvenChecker.test(new MutexEvenGenerator());
	}

}
