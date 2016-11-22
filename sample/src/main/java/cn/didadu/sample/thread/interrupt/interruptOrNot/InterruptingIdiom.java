package cn.didadu.sample.thread.interrupt.interruptOrNot;

import java.util.concurrent.TimeUnit;

public class InterruptingIdiom {

	public static void main(String[] args) throws InterruptedException{
		Thread t = new Thread(new Blocked());
		t.start();
		
		TimeUnit.MILLISECONDS.sleep(5000);
		
		//离开Blocked的run()方法时，确认正确地清理资源
		t.interrupt();
		
	}
}
