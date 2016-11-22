package cn.didadu.sample.concurrentDesign;

/**
 * Created by jinggg on 16/3/17.
 */
public class WaitNotifyTest {

    final static Object object = new Object();

    public static void main(String[] args){

        Thread t1 = new Thread(() -> {
            synchronized (object){
                System.out.println(System.currentTimeMillis()+":T1 start!");
                System.out.println(System.currentTimeMillis()+"T1 wait for object");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+"T1 end!");
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (object){
                System.out.println(System.currentTimeMillis() + ":T2 start!");
                object.notify();
                System.out.println(System.currentTimeMillis() + "T2 end!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }

}
