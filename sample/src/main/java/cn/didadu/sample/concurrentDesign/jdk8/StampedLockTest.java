package cn.didadu.sample.concurrentDesign.jdk8;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock引入乐观读来增加系统的并行读
 * Created by jinggg on 16/3/26.
 */
public class StampedLockTest {

    private double x, y;
    private final StampedLock sl = new StampedLock();

    void move(double deltaX, double deltaY){
        //这是一个排他锁
        long stamp = sl.writeLock();
        try{
            x += deltaX;
            y += deltaY;
        }finally {
            sl.unlockWrite(stamp);
        }
    }

    double distanceFromOrigin(){

        /**
         * 只读方法，视图尝试一次乐观读，返回一个类似时间戳的邮戳整数stamp
         * 这个stamp就可以作为这一次锁获取的凭证
         */
        long stamp = sl.tryOptimisticRead();
        double currentX = x, currentY = y;
        /**
         * validate方法判断这个stamp是否在读过程发生期间被修改过
         */
        if(!sl.validate(stamp)){
            //升级成悲观锁
            stamp = sl.readLock();
            try{
                currentX = x;
                currentY = y;
            }finally {
                sl.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

}
