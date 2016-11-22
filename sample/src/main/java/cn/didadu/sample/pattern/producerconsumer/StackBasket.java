package cn.didadu.sample.pattern.producerconsumer;

public class StackBasket {
	 Mantou sm[] = new Mantou[6];  
	 int index = 0;  
	 
	 public synchronized void push(Mantou m){  
	        try{  
	            while(index == sm.length){  
	                System.out.println("!!!!!!!!!生产满了!!!!!!!!!");  
	                this.wait();  
	            }  
	            this.notify();  
	        }catch(InterruptedException e){  
	            e.printStackTrace();  
	        }catch(IllegalMonitorStateException e){  
	            e.printStackTrace();  
	        }  
	          
	        sm[index] = m;  
	        index++;  
	        System.out.println("生产了：" + m + " 共" + index + "个馒头");  
	 }  
	 
	 public synchronized Mantou pop(){  
	        try{  
	            while(index == 0){  
	                System.out.println("!!!!!!!!!消费光了!!!!!!!!!");  
	                this.wait();  
	            }  
	            this.notify();  
	        }catch(InterruptedException e){  
	            e.printStackTrace();  
	        }catch(IllegalMonitorStateException e){  
	            e.printStackTrace();  
	        }  
	        index--;  
	        System.out.println("消费了：---------" + sm[index] + " 共" + index + "个馒头");  
	        return sm[index];  
	 }  
}
