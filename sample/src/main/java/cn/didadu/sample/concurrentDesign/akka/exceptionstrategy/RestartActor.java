package cn.didadu.sample.concurrentDesign.akka.exceptionstrategy;

import akka.actor.UntypedActor;
import scala.Option;

/**
 * Created by jinggg on 16/3/27.
 */
public class RestartActor extends UntypedActor {

    public enum Msg{
        DONE,RESTART;
    }

    @Override
    public void preStart(){
        System.out.println("preStart hashcode:" + this.hashCode());
    }

    @Override
    public void postStop(){
        System.out.println("postStop hashcode:" + this.hashCode());
    }

    /**
     * 重启完成后调用postRestart()方法，
     * 实际上，Actor重启后的preStart()方法就是在postRestart()中调用的（Actor父类的postRestart()会调用preStart()）
     * @param reason
     * @throws Exception
     */
    @Override
    public void postRestart(Throwable reason) throws Exception{
        super.postRestart(reason);
        System.out.println("postRestart hashcode:" + this.hashCode());
    }

    @Override
    public void preRestart(Throwable reason, Option opt) throws Exception{
        System.out.println("preRestart hashcode:" + this.hashCode());
    }

    @Override public void onReceive(Object message) throws Exception {
        if(message == Msg.DONE){
            getContext().stop(getSelf());
        }else if(message == Msg.RESTART){
            System.out.println(((Object) null).toString());
            //抛出异常 默认会被restart，但这里会resume
            //double a = 0/0;
        }
        unhandled(message);
    }
}
