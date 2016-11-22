package cn.didadu.sample.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
	public static void main(String[] args){
		
		//通过Executors管理Thread对象
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i = 0;i<10;i++){
			executor.execute(new ThreadTest());
		}
		executor.shutdown();
		
	}
}
