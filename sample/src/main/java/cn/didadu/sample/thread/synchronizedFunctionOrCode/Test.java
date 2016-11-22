package cn.didadu.sample.thread.synchronizedFunctionOrCode;

public class Test {

	public static void main(String[] args){
		FunctionAndBlock functionAndBlock = new FunctionAndBlock();
		
		Thread thread1 = new Thread(new SyncnizedThread(functionAndBlock,"method"));
		Thread thread2 = new Thread(new SyncnizedThread(functionAndBlock,"block"));
		Thread thread3 = new Thread(new SyncnizedThread(functionAndBlock,"syncOBject"));
		
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
