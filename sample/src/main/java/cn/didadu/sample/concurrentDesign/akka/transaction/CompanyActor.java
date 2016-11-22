package cn.didadu.sample.concurrentDesign.akka.transaction;

import akka.actor.UntypedActor;
import akka.transactor.Coordinated;
import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

/**
 * 管理公司账户Actor
 * Created by jinggg on 16/3/28.
 */
public class CompanyActor extends UntypedActor {

    private Ref.View<Integer> count = STM.newRef(100);

    @Override public void onReceive(Object message) throws Exception {
        /**
         * Coordinated表示一个新事物的开始
         *
         */
        if(message instanceof Coordinated){
            final Coordinated c = (Coordinated) message;
            //获得事物的参数，也就是需要转账的金额
            final int downCount = (int) c.getMessage();
            //将employee也加入到当前事物
            STMDemo.employee.tell(c.coordinate(downCount),getSelf());

            try{
                //原子执行块
                c.atomic(() -> {
                    //确认汇款金额大于可用余额
                    if(count.get() < downCount){
                        throw new RuntimeException("less than " + downCount);
                    }
                    //对公司账户进行调整
                    STM.increment(count, -downCount);
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
