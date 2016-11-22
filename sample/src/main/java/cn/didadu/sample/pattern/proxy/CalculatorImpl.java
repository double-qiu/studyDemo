package cn.didadu.sample.pattern.proxy;

public class CalculatorImpl implements Calculator{

	@Override
	public int add(int a, int b) {
		// TODO Auto-generated method stub
		System.out.println(a + b);
		return a + b;
	}

}
