package cn.didadu.sample.thread.cooperate.pipe;

import java.io.IOException;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Sender implements Runnable{

	private Random rand = new Random(47);
	
	private PipedWriter out = new PipedWriter();
	
	public PipedWriter getPipedWriter() {
		return out;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(true){
				for(char c = 'A'; c <= 'Z'; c++){
					out.write(c);
					TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				}
			}
		}catch(IOException e){
			System.out.println(e + "Send write exception");
		}catch(InterruptedException e){
			System.out.println(e + "Send sleep exception");
		}
	}

}
