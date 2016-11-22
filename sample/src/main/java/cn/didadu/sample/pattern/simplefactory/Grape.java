package cn.didadu.sample.pattern.simplefactory;

public class Grape implements Fruit{

	private boolean seedless;
	
	@Override
	public void grow() {
		// TODO Auto-generated method stub
		System.out.println("Grape is growing...");
	}

	@Override
	public void harvest() {
		// TODO Auto-generated method stub
		System.out.println("Grape has been harvested...");
	}

	@Override
	public void plant() {
		// TODO Auto-generated method stub
		System.out.println("Grape has been planted...");
	}

	public boolean isSeedless() {
		return seedless;
	}

	public void setSeedless(boolean seedless) {
		this.seedless = seedless;
	}

}
