package cn.didadu.sample.concurrentDesign.parallelarithmetic.flowline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jinggg on 16/3/23.
 */
public class Multiply implements Runnable{

    public static BlockingQueue<Msg> bg = new LinkedBlockingQueue<Msg>();

    @Override public void run() {
        while (true){
            try {
                Msg msg = bg.take();
                msg.i = msg.i * msg.j;
                Div.bg.add(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
