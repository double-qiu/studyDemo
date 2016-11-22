package cn.didadu.sample.concurrentDesign.parallelarithmetic.flowline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jinggg on 16/3/23.
 */
public class Plus implements Runnable{

    public static BlockingQueue<Msg> bg = new LinkedBlockingQueue<Msg>();

    @Override public void run() {
        while (true){
            Msg msg = null;
            try {
                msg = bg.take();
                msg.j = msg.i + msg.j;
                Multiply.bg.add(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
