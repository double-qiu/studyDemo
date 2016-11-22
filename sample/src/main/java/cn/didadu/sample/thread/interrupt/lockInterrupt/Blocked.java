package cn.didadu.sample.thread.interrupt.lockInterrupt;

public class Blocked implements Runnable{
	BlockedMutex blocked = new BlockedMutex();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Waiting for f() in BlockedMutex");
		blocked.f();
		System.out.println("Broken out from blocked call");
	}
	
}
