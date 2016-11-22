package cn.didadu.sample.pattern.proxy;

/**
 * ProxySubject代理了RealSubject
 */
public class ProxySubject extends Subject{

	private RealSubject realSubject;
	
	public ProxySubject(){
		
	}
	
	@Override
	public void request() {
		// TODO Auto-generated method stub
		preRequest();
		if(realSubject == null){
			realSubject = new RealSubject();
		}
		realSubject.request();
		postRequest();
	}

	private void preRequest(){
		System.out.println("Proxy Start...");
	}
	
	private void postRequest(){
		System.out.println("Proxy End...");
	}
}
