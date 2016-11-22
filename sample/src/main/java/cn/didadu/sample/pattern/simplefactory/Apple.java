package cn.didadu.sample.pattern.simplefactory;

public class Apple implements Fruit{

	private int treeAge;
	
	@Override
	public void grow() {
		// TODO Auto-generated method stub
		System.out.println("Apple is growing...");
	}

	@Override
	public void harvest() {
		// TODO Auto-generated method stub
		System.out.println("Apple has been harvested...");
	}

	@Override
	public void plant() {
		// TODO Auto-generated method stub
		System.out.println("Apple has been planted...");
	}

	public int getTreeAge() {
		return treeAge;
	}

	public void setTreeAge(int treeAge) {
		this.treeAge = treeAge;
	}
	
}
