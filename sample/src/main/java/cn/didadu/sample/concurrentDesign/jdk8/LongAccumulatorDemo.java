package cn.didadu.sample.concurrentDesign.jdk8;

import java.util.Random;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * 识别随机数中最大的数
 * Created by jinggg on 16/3/26.
 */
public class LongAccumulatorDemo {

    public static void main(String[] args) throws InterruptedException {

        LongAccumulator accumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);

        Thread[] ts = new Thread[1000];

        for(int i = 0; i < 1000; i++){
           ts[i] =  new Thread(() -> {
                Random random = new Random();
                long value = random.nextLong();
                accumulator.accumulate(value);
            });
            ts[i].start();
        }

        for(int i = 0; i < 1000; i++){
            ts[i].join();
        }

        System.out.println(accumulator.longValue());
    }
}
