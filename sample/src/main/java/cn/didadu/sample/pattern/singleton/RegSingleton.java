package cn.didadu.sample.pattern.singleton;

import java.util.HashMap;

/**
 * 该类以及子类RegSingletonChild通过存储于HashMap的注册机制
 * 实现了获取不同实例
 */
public class RegSingleton {
	
	static private HashMap m_registry = new HashMap();
	
	static{
		RegSingleton x = new RegSingleton();
		m_registry.put(x.getClass().getName(), x);
	}
	
	protected RegSingleton(){}
	
	static public RegSingleton getInstance(String name){
		if(name == null){
			name = "cn.didadu.sample.pattern.singleton.RegSingleton";
		}
		
		if(m_registry.get(name) == null){
			try {
				m_registry.put(name, Class.forName(name).newInstance());
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return (RegSingleton) m_registry.get(name);
	}
	
	public String about(){
		return "Hello, I am RegSingleton";
	}
	
}
