package cn.didadu.sample.concurrentDesign;

/**
 * Created by jinggg on 16/3/17.
 */
public class DeamonTest {

   public static void main(String[] args) throws InterruptedException {
       Thread t = new Thread(() -> {
           while(true){
               System.out.println("I am alive");
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       });
       //守护线程会在所有用户线程都退出时结束
       t.setDaemon(true);
       t.start();
       Thread.sleep(2000);
   }

}
