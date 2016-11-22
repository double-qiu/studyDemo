package cn.didadu.sample.thread.cooperate.notifyAndNotifyALl;

public class Task implements Runnable{
	static Blocker blocker = new Blocker();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		blocker.waitingCall();
	}
}
