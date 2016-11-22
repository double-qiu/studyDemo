package cn.didadu.sample.thread.interrupt.sleepAndIOAndSynchronized;

import java.util.concurrent.TimeUnit;

public class SleepBLocked implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			TimeUnit.SECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("InterruptedException");
		}
		
		System.out.println("Exiting SleepBlocking.run()");
	}

}
