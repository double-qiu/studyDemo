package cn.didadu.sample.concurrentDesign.akka.lifecycle;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * 定义了一个监视着WatchActor
 * 它会在它的上下文中watch一个Actor
 * Created by jinggg on 16/3/27.
 */
public class WatchActor extends UntypedActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public WatchActor(ActorRef ref){
        getContext().watch(ref);
    }

    @Override public void onReceive(Object message) throws Exception {
        if(message instanceof Terminated){
            System.out.println(
                    String.format("%s has terminated, shutting down system", ((Terminated)message).getActor().path())
            );
            getContext().system().shutdown();
        }else{
            unhandled(message);
        }
    }
}
