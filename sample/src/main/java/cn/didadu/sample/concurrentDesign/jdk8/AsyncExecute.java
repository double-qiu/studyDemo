package cn.didadu.sample.concurrentDesign.jdk8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by jinggg on 16/3/25.
 */
public class AsyncExecute {


    public static Integer cal(Integer para) {
        try {
            System.out.println("calculating...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para * para;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> cal(50));

        System.out.println(future.get());
    }

}
