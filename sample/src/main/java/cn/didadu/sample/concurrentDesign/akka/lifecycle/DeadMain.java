package cn.didadu.sample.concurrentDesign.akka.lifecycle;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * Created by jinggg on 16/3/27.
 */
public class DeadMain {

    public static void main(String[] args){
        //创建ActorSystem实例
        ActorSystem system = ActorSystem.create("deadwatch", ConfigFactory.load("samplehello.conf"));

        //创建MyWorker Actor
        ActorRef worker = system.actorOf(Props.create(MyWorker.class),"worker");

        //创建WatcherActor
        system.actorOf(Props.create(WatchActor.class,worker),"watcher");

        worker.tell(MyWorker.Msg.WORKING, ActorRef.noSender());
        worker.tell(MyWorker.Msg.DONE, ActorRef.noSender());

        /**
         * 可以发送CLOSE消息让对方终止
         * 或者直接毒死对方，让其终止
         */
        //发送CLOSE消息让对方终止
        worker.tell(MyWorker.Msg.CLOSE,ActorRef.noSender());
        //直接毒死对方，让其终止
        //worker.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }

}
