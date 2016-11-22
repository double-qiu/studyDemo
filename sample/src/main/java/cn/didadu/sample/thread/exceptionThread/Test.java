package cn.didadu.sample.thread.exceptionThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
	public static void main(String[] args){
		
		//处理线程中抛出的异常、catch无效
		/*try{
			ExecutorService executor = Executors.newCachedThreadPool();
			executor.execute(new ExceptionThread());
			executor.shutdown();
		}catch(Exception e){
			System.out.println("catch...");
		}*/
		
		
		//简单的方式，在Thread类中是指静态域，设为默认未捕获异常处理器
		/*Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(new ExceptionThread());
		executor.shutdown();*/
		
		
		//有效地处理线程中抛出的异常,未捕获的异常是通过uncaughtException捕获的
		ExecutorService executor = Executors.newCachedThreadPool(new HandlerThreadFactory());
		executor.execute(new ExceptionThread());
		executor.shutdown();
	
		
	}
}
