package cn.didadu.sample.concurrentDesign.akka.helloworld;

import akka.actor.UntypedActor;

/**
 * 定义了一个欢迎者
 * Created by jinggg on 16/3/26.
 */
public class Greeter extends UntypedActor {

    @Override public void onReceive(Object message) throws Exception {
        if(message == Msg.GREET){
            /**
             * 收到GREET消息时，就会在控制台打印"Hello World!"
             * 并且向消息发送方发送DONE消息
             */
            System.out.println("Hello World!");
            getSender().tell(Msg.DONE, getSelf());
        }else {
            unhandled(message);
        }
    }

    /**
     * 定义消息类型
     */
    public static enum Msg{
        GREET, DONE
    }
}
