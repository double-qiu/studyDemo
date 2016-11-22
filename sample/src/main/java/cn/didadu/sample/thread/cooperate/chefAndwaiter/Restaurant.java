package cn.didadu.sample.thread.cooperate.chefAndwaiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Restaurant {

	Meal meal;
	ExecutorService exec = null;
	WaitPerson waitPerson = null;
	Chef chef = null;
	
	public static void main(String[] args){
		
		Restaurant restaurant = new Restaurant();
		
		restaurant.exec = Executors.newCachedThreadPool();
		restaurant.waitPerson = new  WaitPerson(restaurant);
		restaurant.chef = new Chef(restaurant);
		
		restaurant.exec.execute(restaurant.waitPerson);
		restaurant.exec.execute(restaurant.chef);
	}
	
}
