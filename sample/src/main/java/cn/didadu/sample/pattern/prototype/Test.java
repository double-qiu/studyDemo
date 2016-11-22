package cn.didadu.sample.pattern.prototype;

/**
 * 原型模式
 * 目的是克隆出一个一模一样的对象
 */
public class Test {

	public static void main(String[] args){
		Client client = new Client();
		
		Prototype oldP = new ConcretePrototype(10);
		
		ConcretePrototype newP = client.operation(oldP);
		System.out.println(newP.getId());
	}
}
