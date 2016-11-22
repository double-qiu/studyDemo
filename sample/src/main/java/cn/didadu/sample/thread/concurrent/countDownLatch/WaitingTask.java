package cn.didadu.sample.thread.concurrent.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class WaitingTask implements Runnable{


	private static int counter = 0;
	
	private final int id = counter++;

	private final CountDownLatch latch;
	
	public WaitingTask(CountDownLatch latch) {
		// TODO Auto-generated constructor stub
		this.latch = latch;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			latch.await();
			System.out.println("Latch barrier passed for " + this);
		}catch(InterruptedException e){
			System.out.println(this + " interrupted");
		}
	}

	public String toString(){
		return String.format("WatingTask %1$-3d ", id);
	}
}
