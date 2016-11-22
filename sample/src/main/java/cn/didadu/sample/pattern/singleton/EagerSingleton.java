package cn.didadu.sample.pattern.singleton;

/**
 * 饿汉单例模式，通过私有构造方法和公共的实例返回方法实现
 * 编译的时候就加载了实例
 */
public class EagerSingleton {
	
	private static final EagerSingleton instance = new EagerSingleton();
	
	private EagerSingleton(){
		
	}
	
	public static EagerSingleton getInstance(){
		return instance;
	}
}
