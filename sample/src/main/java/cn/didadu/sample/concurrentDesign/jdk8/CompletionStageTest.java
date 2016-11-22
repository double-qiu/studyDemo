package cn.didadu.sample.concurrentDesign.jdk8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by jinggg on 16/3/26.
 */
public class CompletionStageTest {

    public static Integer calc(Integer para){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para * para;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

       //流式调用
       /* CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50))
                .thenApply(i -> Integer.toString(i))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);*/

        //处理异常
       /* CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calException(50))
                .exceptionally(ex -> {
                    System.out.println(ex.toString());
                    return 0;
                })
                .thenApply(i -> Integer.toString(i))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);*/

        //组合多个CompletableFuture
        /*CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50))
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> calCompos(i)))
                .thenApply(i -> Integer.toString(i))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);*/

        //另外一种组合方式
        CompletableFuture<Integer> intFuture = CompletableFuture.supplyAsync(() -> calc(50));
        CompletableFuture<Integer> intFuture2 = CompletableFuture.supplyAsync(() -> calc(25));
        CompletableFuture<Void> fu = intFuture.thenCombine(intFuture2, (i,j) -> i + j)
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);

        fu.get();
    }

    public static Integer calException(Integer para){
        return para / 0;
    }

    public static Integer calCompos(Integer para){
        return para / 2;
    }


}
