package cn.didadu.sample.pattern.producerconsumer;

public class ProducerConsumer {
	public static void main(String[] args) {
        StackBasket s = new StackBasket();  
        Producer p = new Producer(s);  
        Consumer c = new Consumer(s);  
        Thread tp = new Thread(p);  
        Thread tc = new Thread(c);  
        tp.start();  
        tc.start();  
    }  
}
