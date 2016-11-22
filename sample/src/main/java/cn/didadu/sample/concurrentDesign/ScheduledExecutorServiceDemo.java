package cn.didadu.sample.concurrentDesign;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by jinggg on 16/3/18.
 */
public class ScheduledExecutorServiceDemo {

    public static void main(String[] args){
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);

        //启动之后每隔两秒实行一次任务
        ses.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(System.currentTimeMillis() / 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },0,2, TimeUnit.SECONDS);
    }
}
