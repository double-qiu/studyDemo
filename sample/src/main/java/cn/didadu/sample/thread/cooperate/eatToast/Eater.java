package cn.didadu.sample.thread.cooperate.eatToast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Eater  implements Runnable{

	private ToastQueue finishedQueue;
	
	private int counter = 0;
	
	public Eater(ToastQueue finished){
		this.finishedQueue = finished;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(!Thread.interrupted()){
				Toast t = finishedQueue.take();
				if(t.getId() != counter++ || t.getStatus() != Toast.Status.JAMMED){
					System.out.println(">>>> Error: " + t);
					System.exit(1);
				}else{
					System.out.println("Chomp! " + t);
				}
			}
		}catch(InterruptedException e){
			System.out.println("Jammer interrupted");
		}
		
		System.out.println("Eater off");
	}
	
	
	public static void main(String[] args) throws InterruptedException{
		ToastQueue dryQueue = new ToastQueue();
		ToastQueue butteredQueue = new ToastQueue();
		ToastQueue finishedQueue = new ToastQueue();
		
		ExecutorService  exec = Executors.newCachedThreadPool();
		exec.execute(new Toaster(dryQueue));
		exec.execute(new Butter(dryQueue,butteredQueue));
		exec.execute(new Jammer(butteredQueue,finishedQueue));
		exec.execute(new Eater(finishedQueue));
		
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
	
	
}
