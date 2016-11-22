package cn.didadu.sample.concurrentDesign;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jinggg on 16/3/17.
 */
public class FairLock implements Runnable{

    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override public void run() {
        while(true){
            try{
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+"获得锁");
            }finally {
                fairLock.unlock();
            }
        }

    }

    public static void main(String[] args){
        FairLock lock = new FairLock();
        Thread t1 = new Thread(lock,"Thread_1");
        Thread t2 = new Thread(lock,"Thread_2");
        t1.start();
        t2.start();
    }
}
