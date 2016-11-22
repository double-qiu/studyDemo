package cn.didadu.sample.thread.shareResource;

public class SynchronizedEvenGenerator extends IntGenerator {

	private int currentEvenValue = 0;

	@Override
	public synchronized int next() {
		// TODO Auto-generated method stub
		++currentEvenValue;
		Thread.yield();
		++currentEvenValue;
		return currentEvenValue;
	}

	public static void main(String[] args){
		//同步了next()方法，共享资源的访问不会出问题
		EvenChecker.test(new SynchronizedEvenGenerator());
	}
}
