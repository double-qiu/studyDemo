package cn.didadu.sample.concurrentDesign.akka.router;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinggg on 16/3/27.
 */
public class WatchWorker extends UntypedActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Router router;
    {
        List<Routee> routees = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            ActorRef worker = getContext().actorOf(Props.create(MyWorker.class),"worker_" + i);
            routees.add(new ActorRefRoutee(worker));
        }
        router = new Router(new RoundRobinRoutingLogic(),routees);
    }

    @Override public void onReceive(Object message) throws Exception {
        if(message instanceof MyWorker.Msg){
            router.route(message,getSender());
        }else if(message instanceof Terminated){
            /*router = router.removeRoutee(((Terminated)message).actor());
            System.out.println(((Terminated)message).actor().path() + " is closed, routees = " + router.routees().size());
            if(router.routees().size()== 0){
                System.out.println("Close system");
                RouteMain.flag.send(false);
                getContext().system().shutdown();
            }*/
        }else{
            unhandled(message);
        }
    }
}
