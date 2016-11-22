package cn.didadu.sample.thread.cooperate.eatToast;

public class Butter implements Runnable{

	private ToastQueue dryQueue;
	
	private ToastQueue butteredQueue;
	
	public Butter(ToastQueue dry,ToastQueue buttered){
		this.dryQueue = dry;
		this.butteredQueue = buttered;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(!Thread.interrupted()){
				Toast t = dryQueue.take();
				t.butter();
				System.out.println(t);
				butteredQueue.put(t);
			}
		}catch(InterruptedException e){
			System.out.println("Butterer interrupted");
		}
		
		System.out.println("Butterer off");
	}

}
