package cn.didadu.sample.concurrentDesign;

import java.util.concurrent.locks.ReentrantLock;

/**
 * lockInterruptibly对中断响应的锁请求
 * 当线程收到中断请求，会自动释放持有的锁
 * 当出现死锁的时候可以
 * Created by jinggg on 16/3/17.
 */
public class IntLock implements Runnable{

    public static ReentrantLock lock1 = new ReentrantLock();

    public static ReentrantLock lock2 = new ReentrantLock();

    int lock;
    public IntLock(int lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if(lock == 1){
                lock1.lockInterruptibly();
                Thread.sleep(500);
                lock2.lockInterruptibly();
                System.out.println("获得lock2，线程得意正常运行");
            }else{
                lock2.lockInterruptibly();
                Thread.sleep(500);
                lock1.lockInterruptibly();
                System.out.println("获得lock1，线程得意正常运行");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(lock1.isHeldByCurrentThread()){
                lock1.unlock();
            }
            if(lock2.isHeldByCurrentThread()){
                lock2.unlock();
            }

            System.out.println(Thread.currentThread().getId()+"：线程退出");
        }
    }



    public static void main(String[] args) throws InterruptedException {
        IntLock r1 = new IntLock(1);
        IntLock r2 = new IntLock(2);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        Thread.sleep(1000);

        t2.interrupt();

    }
}
