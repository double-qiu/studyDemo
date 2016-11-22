package cn.didadu.sample.thread.cooperate.chefAndwaiter;

public class WaitPerson implements Runnable{

	private Restaurant restaurant;
	
	public WaitPerson(Restaurant restaurant){
		this.restaurant = restaurant;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(!Thread.interrupted()){
				
				//waitPerson对象的锁
				synchronized(this){
					while(restaurant.meal == null){
						//当没有膳食可取时，等待
						wait();
					}
				}
				System.out.println("WaitPerson got " + this.restaurant.meal);
				
				//chef对象的锁
				synchronized(restaurant.chef){
					//取膳食，并唤醒chef任务
					restaurant.meal = null;
					restaurant.chef.notifyAll();
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("WaitPerson interruped");
		}
	}
	
}
