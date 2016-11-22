package cn.didadu.sample.thread.deadLock;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable{

	private Chopstick left;
	
	private Chopstick right;
	
	private final int id;
	
	private final int ponderFactor;
	
	private Random rand = new Random(47);
	
	private void pause() throws InterruptedException{
		if(ponderFactor == 0){
			return;
		}
		
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
	}
	
	public Philosopher(Chopstick left,Chopstick right,int ident,int ponder){
		this.left = left;
		this.right = right;
		this.id = ident;
		this.ponderFactor = ponder;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(!Thread.interrupted()){
				//哲学家进入思考
				System.out.println(this + " " + "thinking");
				pause();
				
				//试图拿起右边的筷子
				System.out.println(this + " " + "grabbing right");
				right.take();
				
				//数图拿起左边的筷子
				System.out.println(this + " " + "grabbing left");
				left.take();
				
				//哲学家拿到筷子，进食
				System.out.println(this + " " + "eating");
				pause();
				
				//放下筷子
				right.drop();
				left.drop();
			}
		}catch(InterruptedException e){
			System.out.println(this + " " + "exiting via interrupt");
		}
	}

	public String toString(){
		return "Philosopher " + id;	
	}
}
