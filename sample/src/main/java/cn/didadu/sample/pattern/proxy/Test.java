package cn.didadu.sample.pattern.proxy;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Vector;

public class Test {
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		//静态代理
		Subject subject = new ProxySubject();
		subject.request();
		
		//动态代理（调用的时候生成代理类）
		Calculator calculator = new CalculatorImpl();
		LogHandler lh = new LogHandler(calculator);
		Calculator proxy = (Calculator) Proxy.newProxyInstance(calculator.getClass().getClassLoader(), calculator.getClass().getInterfaces(), lh);
		proxy.add(1, 2);
		
		//动态代理(调用的时候传递对象，代理类中根据对象返回具体的代理类实例)
		Vector<String> v = new Vector<String>(10);
		//proxyList代理了v，从而实现调用v.add方法时可以添加其他操作
		List<String> proxyList = null;
		proxyList = (List<String>) VectorProxy.factory(v);
		proxyList.add("new");
		proxyList.add("haha");
		proxyList.remove(0);
	}

}
