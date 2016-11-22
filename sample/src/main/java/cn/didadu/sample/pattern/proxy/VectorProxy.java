package cn.didadu.sample.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class VectorProxy implements InvocationHandler{

	private Object proxyObj;
	
	public VectorProxy(Object obj){
		this.proxyObj = obj;
	}
	
	public static Object factory(Object obj){
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),new VectorProxy(obj));
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("before calling " + method);
		
		if(args != null){
			for(int i = 0;i < args.length;i++){
				System.out.println(args[i]);
			}
		}
		
		Object o = method.invoke(proxyObj, args);
		
		System.out.println("after calling " + method);
		return o;
	}
}
