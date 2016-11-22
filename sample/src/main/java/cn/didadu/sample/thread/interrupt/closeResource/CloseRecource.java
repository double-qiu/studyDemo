package cn.didadu.sample.thread.interrupt.closeResource;

import cn.didadu.sample.thread.interrupt.sleepAndIOAndSynchronized.IOBlocked;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class CloseRecource {

	public static void main(String[] args) throws IOException, InterruptedException{
		ExecutorService exec = Executors.newCachedThreadPool();
		
		ServerSocket server = new ServerSocket(8080);
		InputStream sockedInput = new Socket("localhost",8080).getInputStream();
		
		exec.execute(new IOBlocked(sockedInput));
		exec.execute(new IOBlocked(System.in));
		
		TimeUnit.MILLISECONDS.sleep(100);
		System.out.println("Shutting down all trheads");
		exec.shutdownNow();
		
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Closing " + sockedInput.getClass().getName());
		//sockedInput关闭，阻塞中断
		sockedInput.close();
		
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Closing " + System.in.getClass().getName());
		//不知为何，System.in关闭时，阻塞没有中断
		System.in.close();
		
	}
}
