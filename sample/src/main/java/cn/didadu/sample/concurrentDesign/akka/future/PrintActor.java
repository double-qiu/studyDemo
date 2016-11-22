package cn.didadu.sample.concurrentDesign.akka.future;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by jinggg on 16/3/27.
 */
public class PrintActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static enum Msg{
        Done, CLOSE, Msg;
    }

    @Override public void onReceive(Object message) throws Exception {
        if(message instanceof Integer){
            System.out.println("Printer:" + message);
        }
        if(message == Msg.Done){
            log.info("Stop working");
        }
        if(message == Msg.CLOSE){
            log.info("Printer will shutdown");
            getSender().tell(Msg.CLOSE,getSelf());
            getContext().stop(getSelf());
        }else{
            unhandled(message);
        }
    }
}
