package cn.didadu.sample.thread.threadLocal;

public class Accessor implements Runnable{

	private final int id;
	
	public Accessor(int idn){
		id = idn;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 5; i++){
			ThreadLocalVariableHolder.increment();
			System.out.println(this);
		}
		
	}

	public String toString(){
		return "#" + id + ": " + ThreadLocalVariableHolder.get();
	}
}
