package cn.didadu.sample.thread.cooperate.waxAndBuffer2;

import java.util.concurrent.TimeUnit;

public class WaxOff implements Runnable{

	private Car car;
	
	public WaxOff(Car c){
		this.car = c;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(!Thread.interrupted()){
				//等待打蜡
				car.waitForWaxing();
				System.out.println("Wax Off! ");
				TimeUnit.MILLISECONDS.sleep(200);
				//抛光
				car.buffed();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Exiting via interrupt! ");
		}
		
		System.out.println("Ending Wax Off task ");
	}
	
	
}
