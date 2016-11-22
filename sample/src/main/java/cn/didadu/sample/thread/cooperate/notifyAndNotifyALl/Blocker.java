package cn.didadu.sample.thread.cooperate.notifyAndNotifyALl;

public class Blocker {

	synchronized void waitingCall(){
		try {
			while(!Thread.interrupted()){
				wait();
				System.out.print(Thread.currentThread() + " ");
			}			
		} catch (InterruptedException e) {
			System.out.println("Exit By InterruptedException...");
		}
	}
	
	synchronized void prod(){
		notify();
	}
	
	synchronized void prodAll(){
		notifyAll();
	}
}
