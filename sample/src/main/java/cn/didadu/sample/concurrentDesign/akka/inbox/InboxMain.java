package cn.didadu.sample.concurrentDesign.akka.inbox;

import akka.actor.*;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by jinggg on 16/3/27.
 */
public class InboxMain {

    public static void main(String[] args) throws TimeoutException {

        ActorSystem system = ActorSystem.create("inboxdemo", ConfigFactory.load("samplehello.conf"));

        ActorRef worker = system.actorOf(Props.create(MyWorker.class), "worker");

        final Inbox inbox = Inbox.create(system);
        inbox.watch(worker);
        inbox.send(worker, MyWorker.Msg.WORKING);
        inbox.send(worker,MyWorker.Msg.DONE);
        inbox.send(worker,MyWorker.Msg.CLOSE);

        while (true){
            /**
             * 此处会阻塞等待消息返回
             * actor中不适合执行耗时的代码，可能会导致其他Actor的调度出问题
             */
            Object msg = inbox.receive(Duration.create(1, TimeUnit.MINUTES));
            if(msg == MyWorker.Msg.CLOSE){
                System.out.println("My worker is closing");
            }else if(msg instanceof Terminated){
                System.out.println("My work is dead");
                system.shutdown();
                break;
            }else{
                System.out.println(msg);
            }
        }


    }

}
