package cn.didadu.sample.thread.interrupt.interruptOrNot;

public class NeedsCleanup {

	private final int id;
	
	public NeedsCleanup(int id){
		this.id = id;
		System.out.println("NeedsCleanup " + id);
	}
	
	public void cleanup(){
		System.out.println("Cleaning up " + id);
	}
	
}
