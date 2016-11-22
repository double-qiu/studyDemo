package cn.didadu.sample.thread.deadLock;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLookcingDingingPhilosophers{

	public static void main(String[] args) throws IOException{
		
		//思考、进食的时间，该时间越小，在Chopstick尚产生的竞争越激烈，死锁会越快发生
		int ponder = 5;
		
		//哲学家、筷子的数量
		int size = 5;
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		Chopstick[] sticks = new Chopstick[size];
		
		for(int i = 0; i < size; i++){
			sticks[i] = new Chopstick();
		}
		
		for(int i = 0; i < size; i++){
			//0--1;1--2;2--3;3--4;4--0 ： 筷子
			//每个哲学家都先拿右边的筷子，会产生死锁
			exec.execute(new Philosopher(sticks[i],sticks[(i+1) % size],i,ponder));
			
			//最后一个哲学家先拿左边的筷子（构造函数中将左、右筷子颠倒），可以防止死锁
			/*if(i < (size - 1)){
				exec.execute(new Philosopher(sticks[i],sticks[i+1],i,ponder));
			}else{
				exec.execute(new Philosopher(sticks[0],sticks[i],i,ponder));
			}*/
		}
		
		System.out.println("Press 'Enter' to quit");
		System.in.read();
		
		exec.shutdownNow();
	}
}
