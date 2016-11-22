package cn.didadu.sample.concurrentDesign;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by jinggg on 16/3/17.
 */
public class ReadWriteLockDemo {

    private static Lock reentrantLock = new ReentrantLock();

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();

    private int value;

    public int handleRead(Lock lock) throws InterruptedException {
        try{
            lock.lock();
            System.out.println("Get lock in handleRead, value is " + value);
            Thread.sleep(1000);
            return value;
        }finally {
            lock.unlock();
        }
    }

    public void handleWrite(Lock lock,int index) throws InterruptedException {
        try{
            lock.lock();
            System.out.println("Get lock in handleWrite, value is " + index);
            Thread.sleep(1000);
            value = index;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args){
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();

        /**
         * readLock读可以并行，不会阻塞读，但是会阻塞写
         */
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleRead(readLock);
                   // demo.handleRead(reentrantLock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        /**
         * writeLock会阻塞写，也会阻塞读
         */
        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleWrite(writeLock, new Random().nextInt());
                    //demo.handleWrite(reentrantLock, new Random().nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        for(int i = 0; i < 18; i++){
            new Thread(readRunnable).start();
        }

        for(int i = 18; i < 20; i++){
            new Thread(writeRunnable).start();
        }

    }
}
