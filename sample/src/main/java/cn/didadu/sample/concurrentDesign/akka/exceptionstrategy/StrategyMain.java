package cn.didadu.sample.concurrentDesign.akka.exceptionstrategy;

import akka.actor.*;
import com.typesafe.config.ConfigFactory;

/**
 * Created by jinggg on 16/3/27.
 */
public class StrategyMain {

    public static void main(String[] args){
        //创建全局ActorSystem
        ActorSystem system = ActorSystem.create("lifecycle", ConfigFactory.load("samplehello.conf"));

        /**
         * 创建Supervisor，并且对Supervisor发送一个RestartActor的Props
         * 这个消息会使得Supervisor创建RestartActor
         */
        ActorRef a = system.actorOf(Props.create(Supervisor.class),"Supervisor");
        a.tell(Props.create(RestartActor.class),ActorRef.noSender());

        /**
         * 选中RestartActor实例，向这个RestartActor发送100挑RESTART消息，这会使得RestartActor抛出NullPointerException
         */
        ActorSelection sel = system.actorSelection("akka://lifecycle/user/Supervisor/restartActor");
        for(int i = 0; i < 100; i++){
            sel.tell(RestartActor.Msg.RESTART, ActorRef.noSender());
        }

    }

}
