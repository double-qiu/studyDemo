package cn.didadu.sample.thread.threadLocal;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalVariableHolder {

	//每个线程都会创建自己独立的副本，每个线程维护自己的value
	private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
		private Random rand = new Random(47);
		protected synchronized Integer initialValue(){
			return rand.nextInt(10000);
		}
	};
	
	public static void increment(){
		value.set(value.get() + 1);
	}
	
	public static int get(){
		return value.get();
	}
	
	public static void main(String[] args){
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < 5; i++){
			exec.execute(new Accessor(i));
		}
		exec.shutdown();
	}
}
