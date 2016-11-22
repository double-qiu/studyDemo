package cn.didadu.sample.concurrentDesign.parallelarithmetic.parallelsort;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jinggg on 16/3/24.
 */
public class ParallelBubbleSort {

    static int exchFlag = 1;

    static synchronized void setExchFlag(int v){
        exchFlag = v;
    }

    static synchronized int getExchFlag(){
        return exchFlag;
    }

    static int score[] = { 67, 88, 75, 120, 55, 55, 99, 100 };

    static ExecutorService pool = Executors.newCachedThreadPool();

    public static class OddEvenSortTask implements Runnable{

        int i;
        CountDownLatch latch;

        public OddEvenSortTask(int i, CountDownLatch latch){
            this.i = i;
            this.latch = latch;
        }

        @Override public void run() {
            if(score[i] > score[i + 1]){
                int temp = score[i];
                score[i] = score[i + 1];
                score[i + 1] = temp;
                setExchFlag(1);
            }
            latch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int start = 0;
        while(getExchFlag() == 1 || start == 1){
            setExchFlag(0);
            //偶数的数组长度，当start=1时，只有leng/2-1个线程
            CountDownLatch latch = new CountDownLatch(score.length/2 - (score.length % 2 == 0 ? start : 0));
            for(int  i = start; i < score.length - 1; i += 2){
                pool.submit(new OddEvenSortTask(i,latch));
            }

            //等待线程结束
            latch.await();

            System.out.print("本次排序结果：");
            for (int a = 0; a < score.length; a++) {
                System.out.print(score[a] + "\t");
            }
            System.out.println("本次的:exchFlag = " + exchFlag + " && start = " + start);

            if(start == 0){
                start = 1;
            }else{
                start = 0;
            }
        }
        pool.shutdown();

        System.out.print("最终排序结果：");
        for (int a = 0; a < score.length; a++) {
            System.out.print(score[a] + "\t");
        }
    }

}
