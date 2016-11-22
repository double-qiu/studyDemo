package cn.didadu.sample.thread.interrupt.lockInterrupt;

import java.util.concurrent.TimeUnit;

public class Interrupting {

	public static void main(String[] args) throws InterruptedException{
		Thread t = new Thread(new Blocked());
		t.start();
		
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Issuing t.interrupt()");
		t.interrupt();
	}
}
