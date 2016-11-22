package cn.didadu.sample.pattern.prototype;

public class Client {
	
	public ConcretePrototype operation(Prototype example){
		return (ConcretePrototype) example.clone();
	}
	
}
