package cn.didadu.sample.concurrentDesign;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**CyclicBarrier可以在所有线程结束之后完成固定的任务
 * Created by jinggg on 16/3/18.
 */
public class CyclicBarrierDemo {

    public static class Soldier implements Runnable{

        private String soldier;

        private final CyclicBarrier cyclic;

        public Soldier(CyclicBarrier cyclic,String soldierName){
            this.cyclic = cyclic;
            this.soldier = soldierName;
        }

        @Override public void run() {
            try {
                //等待所有士兵到齐
                cyclic.await();
                doWork();
                //等待所有士兵完成工作
                cyclic.await()
;            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                //BrokenBarrierException表示当前CyclicBarrier已经破损，无法等待所有线程都到齐
                e.printStackTrace();
            }
        }

        void doWork(){
            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier + ":任务完成");
        }
    }

    public static class BarrireRun implements Runnable{

        boolean flag;
        int N;

        public BarrireRun(boolean flag, int N){
            this.flag = flag;
            this.N = N;
        }

        @Override public void run() {
            if(flag){
                System.out.println("司令：【士兵" + N + "个，任务完成！】");
            }else{
                System.out.println("司令：【士兵" + N + "个，集合完毕！】");
                flag = true;
            }
        }
    }

    public static void main(String[] args){
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N,new BarrireRun(flag,N));

        //设置屏障点，主要是为了执行这个方法
        System.out.println("集合队伍！");

        for(int i = 0; i < N; i++){
            System.out.println("士兵" + i+ "报道！");
            allSoldier[i] = new Thread(new Soldier(cyclicBarrier,"士兵" + i));
            allSoldier[i].start();
        }
    }

}
