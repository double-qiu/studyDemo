package cn.didadu.sample.concurrentDesign.akka.transaction;

import akka.actor.UntypedActor;
import akka.transactor.Coordinated;
import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

/**
 * Created by jinggg on 16/3/28.
 */
public class EmployeeActor extends UntypedActor{

    private Ref.View<Integer> count = STM.newRef(50);

    @Override public void onReceive(Object message) throws Exception {

       if(message instanceof Coordinated){
           final Coordinated c = (Coordinated) message;
           //获得事物的参数，也就是需要转账的金额
           final int downCount = (int) c.getMessage();

           try{
               c.atomic(() -> {
                   STM.increment(count, downCount);
               });
           }catch (Exception e){
               e.printStackTrace();
           }
       }else if("GetCount".equals(message)){
           getSender().tell(count.get(), getSelf());
       }else{
           unhandled(message);
       }


    }
}
