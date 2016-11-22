package cn.didadu.sample.pattern.singleton;

/**
 * 懒汉单例模式
 * 类编译的时候不初始化instance，当第一是调用getInstance方法时初始化instance实例
 */
public class LazySingleton {

	private static LazySingleton instance = null;
	
	private LazySingleton(){}
	
	synchronized public static LazySingleton getInstance(){
		if(instance == null){
			instance = new LazySingleton();
		}
		return instance;
	} 
}
