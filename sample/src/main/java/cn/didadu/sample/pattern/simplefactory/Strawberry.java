package cn.didadu.sample.pattern.simplefactory;

public class Strawberry implements Fruit{

	@Override
	public void grow() {
		// TODO Auto-generated method stub
		System.out.println("Strawberry is growing...");
	}

	@Override
	public void harvest() {
		// TODO Auto-generated method stub
		System.out.println("Strawberry has been harvested...");
	}

	@Override
	public void plant() {
		// TODO Auto-generated method stub
		System.out.println("Strawberry has been planted...");
	}

}
