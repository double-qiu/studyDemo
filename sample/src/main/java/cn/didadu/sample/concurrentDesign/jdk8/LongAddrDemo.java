package cn.didadu.sample.concurrentDesign.jdk8;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 实测下来，目前还是Atomic的性能最好
 * Created by jinggg on 16/3/26.
 */
public class LongAddrDemo {

    private static final int MAX_THREADS = 50; //线程数
    private static final int TASK_COUNT = 50; //任务数
    private static final int TARGET_COUNT = 1000000000; //目标总数

    private AtomicLong acount = new AtomicLong(0L);
    private LongAdder lacount = new LongAdder();
    private long count = 0;

    static CountDownLatch cdlsync = new CountDownLatch(TASK_COUNT);
    static CountDownLatch cdlatomic = new CountDownLatch(TASK_COUNT);
    static CountDownLatch dcladdr = new CountDownLatch(TASK_COUNT);

    /**
     * 有锁的方法
     * @return
     */
    protected synchronized long inc(){
        return ++count;
    }

    protected synchronized long getCount(){
        return count;
    }

    public class SyncThread implements Runnable{

        protected String name;
        protected long startTime;
        LongAddrDemo out;

        public SyncThread(LongAddrDemo o, long startTime){
            this.out = o;
            this.startTime = startTime;
        }

        @Override public void run() {
            long v = out.getCount();
            while (v < TARGET_COUNT){
                v = out.inc();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("SyncThread spend:" + (endTime - startTime) + "ms" + " v=" + v);
            cdlsync.countDown();
        }
    }


    public void testSync() throws InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime = System.currentTimeMillis();
        SyncThread sync = new SyncThread(this,startTime);
        for(int i = 0; i < TASK_COUNT; i++){
            exe.submit(sync);
        }
        cdlsync.await();
        exe.shutdown();
    }

    public class AtomicThread implements Runnable{

        protected String name;
        protected long startTime;

        public AtomicThread(long startTime){
            this.startTime = startTime;
        }

        @Override public void run() {
            long v = acount.get();
            while (v < TARGET_COUNT){
                v = acount.incrementAndGet();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("AtomicThread spend:" + (endTime - startTime) + "ms" + " v=" + v);
            cdlsync.countDown();
        }
    }

    public void testAtomic() throws InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime = System.currentTimeMillis();
        AtomicThread atomic = new AtomicThread(startTime);
        for(int i = 0; i < TASK_COUNT; i++){
            exe.submit(atomic);
        }
        cdlsync.await();
        exe.shutdown();
    }

    public class LongAddrThread implements Runnable{

        protected String name;
        protected long startTime;

        public LongAddrThread(long startTime){
            this.startTime = startTime;
        }

        @Override public void run() {
            long v = lacount.sum();
            while(v < TARGET_COUNT){
                lacount.increment();
                v = lacount.sum();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("LongAddrThread spend:" + (endTime - startTime) + "ms" + " v=" + v);
            cdlsync.countDown();
        }
    }

    public void testLongAddr() throws InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime = System.currentTimeMillis();
        LongAddrThread lat = new LongAddrThread(startTime);
        for(int i = 0; i < TASK_COUNT; i++){
            exe.submit(lat);
        }
        cdlsync.await();
        exe.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        LongAddrDemo lad = new LongAddrDemo();
        //lad.testSync();
        //lad.testAtomic();
        lad.testLongAddr();
    }

}
