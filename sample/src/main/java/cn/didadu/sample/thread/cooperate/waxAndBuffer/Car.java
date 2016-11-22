package cn.didadu.sample.thread.cooperate.waxAndBuffer;

public class Car {

	//true表示已经打蜡
	private boolean waxOn = false;
	
	//打蜡
	public synchronized void waxed(){
		//打蜡
		this.waxOn = true;
		//唤醒抛光线程
		notifyAll();
	}
	
	//抛光
	public synchronized void buffed(){
		//抛光
		this.waxOn = false;
		//唤醒打蜡线程
		notifyAll();
	}
	
	//等待打蜡
	public synchronized void waitForWaxing() throws InterruptedException{
		//false表示已经抛光还没有打蜡，线程挂起，等待打蜡
		while(this.waxOn == false){
			wait();
		}
	}
	
	//等待抛光
	public synchronized void waitForBuffing() throws InterruptedException{
		//true表示已经打蜡，还没有抛光，线程挂起，等待抛光
		while(this.waxOn == true){
			wait();
		}
	}
	
}
