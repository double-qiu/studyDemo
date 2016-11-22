package cn.didadu.sample.jdk8.ch3.sec02;

import java.util.function.*;

public class Parameters {
    public static void main(String[] args) {
        repeat(10, i -> System.out.println("Countdown: " + (9 - i)));
        repeat(10, () -> System.out.println("Hello, World!"));
    }

    /**
     * IntConsumer强制接受int类型的参数，因为需要知道具体发生在哪一次迭代
     * @param n
     * @param action
     */
    public static void repeat(int n, IntConsumer action) {
        for (int i = 0; i < n; i++)
            action.accept(i);
    }

    /**
     * Runnable不需要参数
     * @param n
     * @param action
     */
    public static void repeat(int n, Runnable action) {
        for (int i = 0; i < n; i++)
            action.run();
    }
}
