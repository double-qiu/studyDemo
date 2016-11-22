package cn.didadu.sample.pattern.factorymethod;

/**
 * 工厂方法模式
 * 工厂方法用来生产同一等级结构中的固定产品。（支持增加任意产品）
 */
public class Client {

	private static Creator creator1,creator2;
	private static Product product1,product2;
	
	public static void main(String[] args){
		creator1 = new ConcreteCreator1();
		product1 = creator1.factory();
		
		creator2 = new ConcreteCreator2();
		product2 = creator2.factory(); 
	}
}
