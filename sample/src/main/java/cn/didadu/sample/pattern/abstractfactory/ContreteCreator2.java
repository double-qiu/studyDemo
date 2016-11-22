package cn.didadu.sample.pattern.abstractfactory;

public class ContreteCreator2 implements Creator{

	@Override
	public ProductA factoryA() {
		// TODO Auto-generated method stub
		return new ProductA2();
	}

	@Override
	public ProductB factoryB() {
		// TODO Auto-generated method stub
		return new ProductB2();
	}

}
