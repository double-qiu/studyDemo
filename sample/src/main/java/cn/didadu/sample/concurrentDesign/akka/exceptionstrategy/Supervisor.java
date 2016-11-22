package cn.didadu.sample.concurrentDesign.akka.exceptionstrategy;

import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by jinggg on 16/3/27.
 */
public class Supervisor extends UntypedActor{

    /**
     * 定义了一个OneForOneStrategy的监督策略
     * 在这个监督策略中，运行Actor在遇到错误后，在1分钟内进行3次重试，如果超过这个频率就直接杀死Actor
     *
     */
    private static SupervisorStrategy strategy = new OneForOneStrategy(3, Duration.create(1, TimeUnit.MINUTES),
            new Function<Throwable, SupervisorStrategy.Directive>(){
                @Override
                public SupervisorStrategy.Directive apply(Throwable throwable) {

                    if(throwable instanceof ArithmeticException){
                        //遇到算数异常，继续指定这个Actor，不做任何处理
                        System.out.println("meet ArithmeticException, just resume");
                        return SupervisorStrategy.resume();
                    }else if(throwable instanceof NullPointerException){
                        //遇到空指针时，进行Actor的重启
                        System.out.println("meet NullPointerException, just restart");
                        return SupervisorStrategy.restart();
                    }else if(throwable instanceof IllegalArgumentException){
                        //遇到IllegalArgumentException，直接停止Actor
                        System.out.println("meet IllegalArgumentException, just stop");
                        return SupervisorStrategy.stop();
                    }else{
                        //对于在这个函数中没有涉及的异常，则向上抛出，由更顶层的Actor处理
                        return SupervisorStrategy.escalate();
                    }
                }
            });


    @Override
    public SupervisorStrategy supervisorStrategy(){
        return strategy;
    }

    @Override public void onReceive(Object message) throws Exception {
        if(message instanceof Props){
            getContext().actorOf((Props) message, "restartActor");
        }else{
            unhandled(message);
        }
    }
}
