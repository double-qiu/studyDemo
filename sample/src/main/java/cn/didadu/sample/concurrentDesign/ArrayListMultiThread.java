package cn.didadu.sample.concurrentDesign;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by jinggg on 16/3/17.
 */
public class ArrayListMultiThread {

    //多线程访问ArrayList，线程不安全
    //static ArrayList<Integer> list = new ArrayList<>(10);

    static Vector<Integer> list = new Vector<>(10);


    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 1000000; i++){
                list.add(i);
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 1000000; i++){
                list.add(i);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(list.size());
    }

}
