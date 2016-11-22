package cn.didadu.sample.pattern.singleton;

public class Test {

	public static void main(String[] args){
		String regStr = RegSingleton.getInstance(null).about();
		System.out.println(regStr);
		
		String regCHildStr = RegSingletonChild.getInstance().about();
		System.out.println(regCHildStr);
	}
}
