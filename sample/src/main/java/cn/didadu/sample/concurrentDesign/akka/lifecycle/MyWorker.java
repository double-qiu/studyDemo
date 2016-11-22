package cn.didadu.sample.concurrentDesign.akka.lifecycle;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by jinggg on 16/3/27.
 */
public class MyWorker extends UntypedActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static enum Msg{
        WORKING, DONE, CLOSE;
    }

    /**
     * 可用来初始化资源
     */
    @Override
    public void preStart(){
        System.out.println("MyWorker is staring");
    }

    /**
     * 可用来释放资源
     */
    @Override
    public void postStop(){
        System.out.println("MyWork is stopping");
    }

    @Override public void onReceive(Object message) throws Exception {
        if(message == Msg.WORKING){
            System.out.println("I am working");
        }
        if(message == Msg.DONE){
            System.out.println("Stop working");
        }
        if(message == Msg.CLOSE){
            System.out.println("I will shutdown");
            getSender().tell(Msg.CLOSE, getSelf());
            getContext().stop(getSelf());
        }else{
            unhandled(message);
        }
    }
}
