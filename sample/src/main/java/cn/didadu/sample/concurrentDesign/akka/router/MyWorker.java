package cn.didadu.sample.concurrentDesign.akka.router;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by jinggg on 16/3/27.
 */
public class MyWorker extends UntypedActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static enum Msg{
        WORKING,DONE,CLOSE;
    }

    @Override public void onReceive(Object message) throws Exception {
        if(message == Msg.WORKING){
            log.info("I am working");
        }
        if(message == Msg.DONE){
            log.info("Stop working");
        }
        if(message == Msg.CLOSE){
            log.info("I will shutdown");
            getSender().tell(Msg.CLOSE, getSelf());
            getContext().stop(getSelf());
        }else {
            unhandled(message);
        }
    }
}
