package cn.didadu.sample.concurrentDesign.akka.future;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by jinggg on 16/3/27.
 */
public class MyWorkerActor extends UntypedActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override public void onReceive(Object message) throws Exception {
        if(message instanceof Integer){
            int i = (Integer) message;
            Thread.sleep(1000);
            getSender().tell(i * i, getSelf());
        }
        if(message == PrintActor.Msg.Done){
            log.info("Stop working");
        }
        if(message == PrintActor.Msg.CLOSE){
            log.info("Worker will shutdown");
            getSender().tell(PrintActor.Msg.CLOSE,getSelf());
            getContext().stop(getSelf());
        }else{
            unhandled(message);
        }
    }
}
