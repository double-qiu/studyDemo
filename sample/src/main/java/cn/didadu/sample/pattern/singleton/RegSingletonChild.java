package cn.didadu.sample.pattern.singleton;

public class RegSingletonChild extends RegSingleton{
	
	public RegSingletonChild(){}
	
	static public RegSingletonChild getInstance(){
		return (RegSingletonChild) RegSingleton.getInstance("cn.didadu.sample.pattern.singleton.RegSingletonChild");
	}
	
	public String about(){
		return "Hello, I am RegSingletonChild";
	}
}
