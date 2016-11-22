package cn.didadu.sample.concurrentDesign.akka.helloworld;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by jinggg on 16/3/26.
 */
public class HelloWorld extends UntypedActor{

    ActorRef greeter;

    /**
     * Akka的回调方法，在Actor启动前，会被Akka框架调用，完成一些初始化的工作
     * 在这里我们创建了Greeter实例，并且向它发送GREET消息
     * 此时由于创建Greeter时使用的是HelloWorld的上下文，以你它属于HelloWorld的子Actor
     */
    @Override
    public void preStart(){
        greeter = getContext().actorOf(Props.create(Greeter.class),"greeter");
        System.out.println("Greeter Actor Path:" + greeter.path());
        greeter.tell(Greeter.Msg.GREET,getSelf());
    }

    /**
     * HelloWorld的消息处理函数，在这里只处理DONE的消息
     * 在收到DONE消息后，它会再次向Greeter发送GRREET消息，接着将自己停止
     * @param message
     * @throws Exception
     */
    @Override
    public void onReceive(Object message) throws Exception {
        if(message == Greeter.Msg.DONE){
            greeter.tell(Greeter.Msg.GREET,getSelf());
            getContext().stop(getSelf());
        }else{
            unhandled(message);
        }
    }
}
