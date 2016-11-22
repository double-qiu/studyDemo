package cn.didadu.sample.thread.shareResource;

public class EventGenerator extends IntGenerator{

	private int currentEvenValue = 0;
	
	@Override
	public int next() {
		// TODO Auto-generated method stub
		++currentEvenValue;
		//一个任务有可能在另一个任务执行第一个递增操作之后，就立即调用next()方法
		//*Java中递增不是原子性的操作，因此如果不保护任务，即使单一的递增页不是安全的
		++currentEvenValue;
		return currentEvenValue;
	}
	
	public static void main(String[] args){
		//没有同步next()方法，共享资源的访问会出问题
		EvenChecker.test(new EventGenerator());
	}

}
