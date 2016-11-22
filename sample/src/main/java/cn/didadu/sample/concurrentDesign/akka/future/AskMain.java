package cn.didadu.sample.concurrentDesign.akka.future;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import cn.didadu.sample.concurrentDesign.akka.lifecycle.WatchActor;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

/**
 * Created by jinggg on 16/3/27.
 */
public class AskMain {

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("inboxdemo", ConfigFactory.load("samplehello.conf"));
        ActorRef worker = system.actorOf(Props.create(MyWorkerActor.class), "worker");
        ActorRef printer = system.actorOf(Props.create(PrintActor.class), "printer");
        system.actorOf(Props.create(WatchActor.class,worker),"watch");

        /**
         * 等待future返回
         * 该方法间接地将异步调用转为同步阻塞调用
         */
       /* Future<Object> f =  ask(worker,5,1500);
        int re = (int) Await.result(f, Duration.create(6, TimeUnit.SECONDS));
        System.out.println(re);
        printer.tell(re,ActorRef.noSender());*/

        /**
         * 直接导向其他Actor，pipe不会等待
         */
        Future<Object> f =  ask(worker,5,1500);
        pipe(f, system.dispatcher()).to(printer);
        worker.tell(PoisonPill.getInstance(), ActorRef.noSender());

        worker.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }

}
