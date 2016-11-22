package cn.didadu.sample.thread.interrupt.sleepAndIOAndSynchronized;

import java.io.IOException;
import java.io.InputStream;

public class IOBlocked implements Runnable{

	private InputStream in;
	
	public IOBlocked(InputStream is){
		this.in = is;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Waiting for read()");
			in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(Thread.currentThread().isInterrupted()){
				System.out.println("Interrupted from blocked I/O");
			}else{
				throw new RuntimeException(e);
			}
		}
		
		System.out.println("Exiting IOBlocked.run()");
	}

}
