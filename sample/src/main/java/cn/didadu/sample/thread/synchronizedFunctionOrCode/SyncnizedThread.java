package cn.didadu.sample.thread.synchronizedFunctionOrCode;

public class SyncnizedThread implements Runnable{

	private FunctionAndBlock functionAndBlock;
	
	private String runType;
	
	public SyncnizedThread(FunctionAndBlock functionAndBlock,String runType){
		this.functionAndBlock = functionAndBlock;
		this.runType = runType;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(runType.equals("method")){
			functionAndBlock.synchronizedMethodGet();
		}else if(runType.equals("block")){
			functionAndBlock.synchronizedBlockGet();
		}else if(runType.equals("syncOBject")){
			functionAndBlock.synchronizedObject();
		}
		
	}

}
