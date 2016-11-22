package cn.didadu.sample.concurrentDesign;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jinggg on 16/3/17.
 */
public class TimeLock implements Runnable{

    public static ReentrantLock lock = new ReentrantLock();

    @Override public void run() {
        try {
            if(lock.tryLock(5, TimeUnit.SECONDS)){
                System.out.println(Thread.currentThread().getId()+"get lock");
                Thread.sleep(6000);
            }else{
                System.out.println("get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //判断若当前线程持有锁，则释放该锁
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
                System.out.println(Thread.currentThread().getId()+"release lock");
            }
        }
    }

    public static void main(String[] args){
        TimeLock tl = new TimeLock();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);

        t1.start();
        t2.start();
    }




}
