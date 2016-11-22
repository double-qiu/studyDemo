package cn.didadu.sample.thread.cooperate.rocketLiftOff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 此例子可以作为异步日志的例子
 * @author zj
 *
 */
public class TestBlockingQueues {

	static void getkey(){
		try {
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}
	
	static void getKey(String message){
		System.out.println(message);
		getkey();
	}
	
	static void test(String msg,BlockingQueue<LiftOff> rockets){
		System.out.println(msg);
		
		//启动队列线程
		LiftOffRunner runner = new LiftOffRunner(rockets);
		Thread t = new Thread(runner);
		t.start();
		
		//在同步的阻塞队列中添加元素
		for(int i = 0; i < 5; i++){
			runner.add(new LiftOff(5)); 
		}
		
		getKey("Press 'Enter' (" + msg + ")");
		t.interrupt();
		System.out.println("Fnished " + msg + " test");
	}
	
	public static void main(String[] args){
		test("LinkedBlockingQueue",new LinkedBlockingQueue<LiftOff>());
	}
}
