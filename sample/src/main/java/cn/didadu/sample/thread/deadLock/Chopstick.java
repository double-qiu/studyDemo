package cn.didadu.sample.thread.deadLock;

public class Chopstick {

	//被拿起来是taken为true
	private boolean taken = false;
	
	public synchronized void take() throws InterruptedException{
		//当筷子被拿起是，进入等待
		while(taken){
			wait();
		}
		taken = true;
	}
	
	public synchronized void drop(){
		//当筷子被放下时，唤醒所有等待该筷子的线程
		taken = false;
		notifyAll();
	}
}
