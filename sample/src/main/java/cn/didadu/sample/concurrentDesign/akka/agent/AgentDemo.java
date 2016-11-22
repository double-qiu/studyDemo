package cn.didadu.sample.concurrentDesign.akka.agent;

import akka.actor.*;
import akka.agent.Agent;
import akka.dispatch.ExecutionContexts;
import akka.dispatch.Futures;
import akka.dispatch.OnComplete;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by jinggg on 16/3/28.
 */
public class AgentDemo {

    public static Agent<Integer> counterAgent = Agent.create(0, ExecutionContexts.global());

    static ConcurrentLinkedQueue<Future<Integer>> futures = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws TimeoutException {
        final ActorSystem system = ActorSystem.create("agentDemo", ConfigFactory.load("samplehello.conf"));

        //创建10个CounterActor对象
        ActorRef[] counter = new ActorRef[10];
        for(int i = 0; i < 10; i++){
            counter[i] = system.actorOf(Props.create(CounterActor.class),"counter_" + i);
        }

        //使用Inbox与CounterActor通信
        final Inbox inbox = Inbox.create(system);
        for(int i = 0; i < counter.length; i++){
            //触发累加操作
            inbox.send(counter[i],1);
            inbox.watch(counter[i]);
        }

        int closeCount = 0;
        //等待所有Actor结束
        while (true){
            Object msg = inbox.receive(Duration.create(1, TimeUnit.SECONDS));
            if(msg instanceof Terminated){
                closeCount ++;
                if(closeCount == counter.length){
                    break;
                }else{
                    System.out.println(msg);
                }
            }
        }

        //等待所有累加线程完成，因为他们都是异步的
        Futures.sequence(futures,system.dispatcher()).onComplete(
                new OnComplete<Iterable<Integer>>() {
                    @Override public void onComplete(Throwable failure, Iterable<Integer> success) throws Throwable {
                        System.out.println("counterAgent = " + counterAgent.get());
                        system.shutdown();
                    }
                }, system.dispatcher());
    }
}
