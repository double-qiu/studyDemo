package cn.didadu.sample.concurrentDesign.jdk8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 子线程可以等待主线程给的数据
 * Created by jinggg on 16/3/25.
 */
public class AskThread implements Runnable{

    CompletableFuture<Integer> re = null;

    public AskThread(CompletableFuture<Integer> re){
        this.re = re;
    }

    @Override public void run() {
        int myRe = 0;
        try {
            //阻塞等待其他线程准备的数据
            myRe = re.get() * re.get();
            System.out.println(myRe);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final CompletableFuture<Integer> future = new CompletableFuture<>();

        new Thread(new AskThread(future)).start();

        //模拟长时间计算
        System.out.println("calculating...");
        Thread.sleep(5000);

        //告知结果
        future.complete(60);
    }
}
