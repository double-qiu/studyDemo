package cn.didadu.sample.io;

public class TestPipeStream {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  SendMessage send = new SendMessage();  
	        ReceiveMessage receive = new ReceiveMessage();  
	        try {  
	        	//这个方法就把两个线程”connect“起来了。
	            send.getOut().connect(receive.getInput());  
	            Thread t1 = new Thread(send);  
	            Thread t2 = new Thread(receive);  
	            t1.start();  
	            t2.start();  
	  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	  
	}

}
