package cn.didadu.sample.thread.cooperate.chefAndwaiter;

import java.util.concurrent.TimeUnit;

public class Chef implements Runnable{

	private Restaurant restaurant;
	
	private int count = 0;
	
	public Chef(Restaurant restaurant){
		this.restaurant = restaurant;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			while(!Thread.interrupted()){
				//chef对象的锁
				synchronized(this){
					//当存在膳食时等待
					while(restaurant.meal != null){
						wait();
					}
				}
				
				if(++count == 10){
					//当膳食数量将达到10时，关闭所有任务
					System.out.println("Out of food, closing");
					restaurant.exec.shutdownNow();
				}
				
				System.out.print("Order up! ");
				//waitPerson对象的锁
				synchronized(restaurant.waitPerson){
					//制作膳食，并通知waitPerson任务，可以取膳食
					restaurant.meal = new Meal(count);
					restaurant.waitPerson.notifyAll();
				}
				
				//当shutdownNow执行之后，下面语句的调用，试图进入已经阻塞的chef任务操作，会抛出InterruptedException
				//若将下面语句注释，则chef任务由!Thread.interrupted()的判断正常退出
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Chef interrupted");
		}
	}

}
