package cn.didadu.sample.thread.callable;

import java.util.concurrent.Callable;

public class TaskWithResult implements Callable<String>{

	private int id;
	
	public TaskWithResult(int id){
		this.id = id;
	}

	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		if(id == 1){
			Thread.sleep(5000);
		}else{
			Thread.sleep(100);
		}
		
		return "result of TaskWithResult " + id;
	}
}
