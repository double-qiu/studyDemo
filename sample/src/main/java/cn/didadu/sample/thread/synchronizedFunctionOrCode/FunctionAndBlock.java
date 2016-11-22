package cn.didadu.sample.thread.synchronizedFunctionOrCode;

/**
 * 当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块
 * 然而，当一个线程访问object的一个synchronized(this)同步代码块时，另一个线程仍然可以访问该object中的非synchronized(this)同步代码块
 * 尤其关键的是，当一个线程访问object的一个synchronized(this)同步代码块时，其他线程对object中所有其它synchronized(this)同步代码块的访问将被阻塞
 * 当一个线程访问object的一个synchronized(this)同步代码块时，它就获得了这个object的对象锁。结果，其它线程对该object对象所有同步代码部分的访问都被暂时阻塞
 * synchronizedMethodGet的synchronized锁和this锁是同一把锁,所以synchronizedMethodGet和synchronizedBlockGet方法同时只能有一个在运行
 * @author zj
 *
 */
public class FunctionAndBlock {

	private Object syncOBject = new Object();
	
    public synchronized void synchronizedMethodGet() {
    	while(true){
    		 System.out.println("*************run in method************");
    	}
    }

    public void synchronizedBlockGet() {
        synchronized(this) {
           while(true){
        	   System.out.println("---------------run in block---------------");
           }
        }
    }
    
    /**
     * this对象的锁与syncOBject锁互不影响，是两个完全独立的锁
     */
    public void synchronizedObject(){
    	  synchronized(syncOBject) {
              while(true){
           	   System.out.println("---------------run in syncOBject---------------");
              }
           }
    }
}
