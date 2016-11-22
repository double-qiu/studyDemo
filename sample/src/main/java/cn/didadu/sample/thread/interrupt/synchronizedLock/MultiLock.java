package cn.didadu.sample.thread.interrupt.synchronizedLock;

public class MultiLock {

	private int count;
	
	public MultiLock(int count){
		this.count = count;
	}
	
	public synchronized void f1(){
		System.out.println(Thread.currentThread().getName());		
		if(count-- > 0){
			System.out.println("f1() calling f2() with count " + count);
			f2();
		}
	}
	
	public synchronized void f2(){
		System.out.println(Thread.currentThread().getName());		
		if(count-- > 0){
			System.out.println("f2() calling f1() with count " + count);
			f1();
		}
	}
	
	public static void main(String[] args){
		final MultiLock multiLock = new MultiLock(10);
		
		//该任务已经在第一个对f1()的调用中获得了multiLock对象锁，因此同一个任务将在对f2()的调用中再次获得这个锁，依次类推
		//因此，一个任务能够调用在统一个对象中的其他的synchronized方法
		new Thread(){
			public void run(){
				multiLock.f1();
			}
		}.start();
		
		//一直要到上一个任务执行完成，释放了，该任务才能执行，当该任务执行是，count已经是0了，所以只运行一次f1()就退出任务了
		new Thread(){
			public void run(){
				multiLock.f1();
			}
		}.start();
	}
}
