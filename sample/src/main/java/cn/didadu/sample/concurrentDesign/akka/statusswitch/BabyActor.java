package cn.didadu.sample.concurrentDesign.akka.statusswitch;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

/**
 * Created by jinggg on 16/3/27.
 */
public class BabyActor extends UntypedActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static enum Msg{
        SLEEP, PLAY, CLOSE;
    }

    /**
     * 相当于封装了onReceive处理angry时的逻辑
     */
    Procedure<Object> angry = new Procedure<Object>(){
        @Override public void apply(Object param) throws Exception {
            System.out.println("angryApply:" + param);
            if(param == Msg.SLEEP){
                getSender().tell("I am already angry",getSelf());
                System.out.println("I am already angry");
            }else if(param == Msg.PLAY){
                System.out.println("I like playing");
                getContext().become(happy);
            }
        }
    };

    /**
     * 相当于封装了onReceive处理happy时的逻辑
     */
    Procedure<Object> happy = new Procedure<Object>(){
        @Override public void apply(Object param) throws Exception {
            System.out.println("happyApply:" + param);
            if(param == Msg.PLAY){
                getSender().tell("I am already happy :-",getSelf());
                System.out.println("I am already happy :-");
            }else if(param == Msg.SLEEP){
                System.out.println("I don't want to sleep");
                getContext().become(angry);
            }
        }
    };

    /**
     * 当onReceive处理Sleep消息时，会切换当前Actor状态为angry，如果消息为Play，则切换状态为happy
     * 一点完成状态切换，当后续有新的消息送达时，就不会再由onReceive函数处理了
     * @param message
     * @throws Exception
     */
    @Override public void onReceive(Object message) throws Exception {
        System.out.println("onReceive:" + message);
        if(message == Msg.SLEEP){
            getContext().become(angry);
        }else if(message == Msg.PLAY){
            getContext().become(happy);
        }else{
            unhandled(message);
        }
    }
}
