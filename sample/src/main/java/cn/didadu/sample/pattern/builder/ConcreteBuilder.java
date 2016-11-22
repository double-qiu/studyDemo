package cn.didadu.sample.pattern.builder;

public class ConcreteBuilder extends Builder{

	private Product product = new Product();
	
	@Override
	public void buildPart1() {
		// TODO Auto-generated method stub
		System.out.println("Build part1....");
	}

	@Override
	public void buildPart2() {
		// TODO Auto-generated method stub
		System.out.println("Build part2....");
	}

	@Override
	public Product retrieveProduct() {
		// TODO Auto-generated method stub
		return product;
	}

}
