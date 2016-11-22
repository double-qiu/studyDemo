package cn.didadu.sample.concurrentDesign.jdk8;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock挂起线程是使用的是Unsage.park()函数
 * 该函数在遇到线程中断时会直接返回，不会抛出异常
 * StampedLock的死循环逻辑中没有处理有关中断的逻辑，因此会导致阻塞在park上的线程没中断后会再次进入循环
 * 当退出条件得不到满足时，就会发生疯狂占用CPU的情况
 * Created by jinggg on 16/3/26.
 */
public class StampedLockCPUDemo {

    static Thread[] holdCpuThreads = new Thread[3];
    static final StampedLock lock = new StampedLock();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            long readLock = lock.writeLock();
            LockSupport.parkNanos(600000000000L);
            lock.unlockWrite(readLock);
        }).start();

        Thread.sleep(100);

        for(int i = 0; i < 3; i++){
            holdCpuThreads[i] = new Thread(new HoldCPUReadThread());
            holdCpuThreads[i].start();
        }

        Thread.sleep(30000);

        //线程中断后，会占用CPU
        for(int i = 0; i < 3; i++){
            holdCpuThreads[i].interrupt();
        }
    }

    private static class HoldCPUReadThread implements Runnable{

        @Override public void run() {
            long lockr = lock.readLock();
            System.out.println(Thread.currentThread().getName() + "获得读锁！");
            lock.unlockRead(lockr);
        }
    }


}
