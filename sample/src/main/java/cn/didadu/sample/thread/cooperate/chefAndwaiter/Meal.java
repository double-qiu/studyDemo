package cn.didadu.sample.thread.cooperate.chefAndwaiter;

public class Meal {

	private final int orderNum;
	
	public Meal(int orderNum){
		this.orderNum = orderNum;
	}
	
	public String toString(){
		return "Meal " + this.orderNum;
	}
}
