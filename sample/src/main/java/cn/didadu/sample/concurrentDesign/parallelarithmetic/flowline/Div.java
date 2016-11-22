package cn.didadu.sample.concurrentDesign.parallelarithmetic.flowline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jinggg on 16/3/23.
 */
public class Div implements Runnable{

    public static BlockingQueue<Msg> bg = new LinkedBlockingQueue<Msg>();

    @Override public void run() {
        while (true){
            try {
                Msg msg = bg.take();
                msg.i = msg.i / 2;
                System.out.println(msg.orgStr + "=" + msg.i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
