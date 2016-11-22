package cn.didadu.sample.concurrentDesign;

/**
 * Created by jinggg on 16/3/17.
 */
public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while(true){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Interrupted!");
                    break;
                }
                System.out.println("running......");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted When Sleep");
                    //Thread.sleep()方法由于中断而抛出异常，此时它会清除中断标记，所以需要再次中断自己,否则程序将无限循环
                    //设置中断标识
                    Thread.currentThread().interrupt();
                }
            }
        });
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
    }

}
