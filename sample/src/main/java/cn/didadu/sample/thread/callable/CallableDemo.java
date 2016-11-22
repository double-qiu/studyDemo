package cn.didadu.sample.thread.callable;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableDemo {

	public static void main(String[] args){
		
		//newCachedThreadPool多个线程同时运行
		ExecutorService exec = Executors.newCachedThreadPool();
		//newSingleThreadExecutor单个线程运行，任务排队
		//ExecutorService exec = Executors.newSingleThreadExecutor();
		
		ArrayList<Future<String>> results = new ArrayList<Future<String>>();

		for(int i = 0; i < 10; i++){
			results.add(exec.submit(new TaskWithResult(i)));
		}
		
		for(Future<String> result : results){
			try {
				//results中是之前添加的待完成的任务
				//get()方法会阻塞，一直等result有数据返回才继续执行
				System.out.println(result.get());
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		exec.shutdown();
	}
	
}
