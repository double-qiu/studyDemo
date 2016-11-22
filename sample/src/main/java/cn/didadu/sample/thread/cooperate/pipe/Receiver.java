package cn.didadu.sample.thread.cooperate.pipe;

import java.io.IOException;
import java.io.PipedReader;

public class Receiver implements Runnable{

	private PipedReader in;
	
	public Receiver(Sender sender) throws IOException{
		in = new PipedReader(sender.getPipedWriter());
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(true){
				//调用read，如果没有数据，管道自动阻塞
				System.out.println("Read: " + (char)in.read() + ", ");
			}
		}catch(IOException e){
			System.out.println(e + "Receiver read exception");
		}
	}

}
