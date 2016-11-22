package cn.didadu.sample.thread.cooperate.notifyAndNotifyALl;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NotifyVsNotifyAll {

	public static void main(String[] args) throws InterruptedException{
		ExecutorService exec = Executors.newCachedThreadPool();
		
		for(int i = 0; i < 5; i++){
			exec.execute(new Task());
		}
		exec.execute(new Task2());
		
		Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask(){
			boolean prod = true;
			public void run(){
				if(prod){
					System.out.print("\nnotify()");
					Task.blocker.prod();
					prod = false;
				}else{
					System.out.print("\nnotifyAll()");
					//只会唤醒等待Task锁的线程，无法唤醒等待Task2的锁的线程
					//所以，notifyAll()不是唤醒所有等待线程，而是唤醒所有等待特定锁(Task)的线程
					Task.blocker.prodAll();
					prod = true;
				}
			}
		}, 400, 400);
		
		TimeUnit.SECONDS.sleep(5);
		
		timer.cancel();
		System.out.println("\nTimer cancled");
		
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.print("Task2.blocker.prodAll");
		Task2.blocker.prodAll();
		
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.println("\nShutting down");
		exec.shutdownNow();
	}
}
