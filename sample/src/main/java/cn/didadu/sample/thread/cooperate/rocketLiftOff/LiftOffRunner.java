package cn.didadu.sample.thread.cooperate.rocketLiftOff;

import java.util.concurrent.BlockingQueue;

public class LiftOffRunner implements Runnable{

	//同步的阻塞队列
	private BlockingQueue<LiftOff> rockets;
	
	public LiftOffRunner(BlockingQueue<LiftOff> rockets){
		this.rockets = rockets;
	}
	
	public void add(LiftOff lo){
		try {
			rockets.put(lo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interrupted during put()");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(!Thread.interrupted()){
				//当队列中没有内容是，take()操作会阻塞线程-->await()
				LiftOff rocket = rockets.take();
				//使用当前线程
				rocket.run();
			}
		}catch(InterruptedException e){
			System.out.println("Waking from take()");
		}
		
		System.out.println("Exiting LiftOffRunner");
	}

}
