package cn.didadu.sample.jvm.concurrent;

/**
 * 由于race++不是原子性操作，导致volatile修饰的对象不是线程安全的
 * 另外：volatile会禁止指令重排序优化
 * Created by jinggg on 16/3/16.
 */
public class VolatileTest {

    public static volatile int race = 0;

    public static void increase(){
        race++;
    }

    public static void main(String[] args){
        for(int i = 0; i < 20; i++){
            new Thread(() -> {
               for(int j = 0;j < 10000; j++){
                    increase();
               }
            }).start();
        }

   /*     while(Thread.activeCount() > 1){
            Thread.yield();
        }*/

        System.out.println(race);
    }

}
