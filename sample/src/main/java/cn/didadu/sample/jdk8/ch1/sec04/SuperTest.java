package cn.didadu.sample.jdk8.ch1.sec04;

public class SuperTest {
   public static void main(String[] args) {
      class Greeter {
         public void greet() { 
            System.out.println("Hello, world!"); 
         }
      }

      class ConcurrentGreeter extends Greeter {
         public void greet() {
            /**
             * 当线程启动时会调用Runnable方法，然后执行super::greet并调用父类的greet方法
             */
            Thread t = new Thread(super::greet);
            t.start();
         }
      }

      new ConcurrentGreeter().greet();
   }   
}
