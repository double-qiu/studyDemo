package cn.didadu.sample.thread.cooperate.eatToast;

public class Jammer implements Runnable{

	private ToastQueue butteredQueue;
	
	private ToastQueue finishedQueue;
	
	public Jammer(ToastQueue buttered,ToastQueue finished){
		this.butteredQueue = buttered;
		this.finishedQueue = finished;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(!Thread.interrupted()){
				Toast t = butteredQueue.take();
				t.jam();
				System.out.println(t);
				finishedQueue.put(t);
			}
		}catch(InterruptedException e){
			System.out.println("Jammer interrupted");
		}
		
		System.out.println("Jammer off");
	}
	
	
}
