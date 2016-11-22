package cn.didadu.sample.pattern.proxy;

public class RealSubject extends Subject{

	public RealSubject(){
		
	}
	
	@Override
	public void request() {
		// TODO Auto-generated method stub
		System.out.println("From RealSubject.");
	}

}
