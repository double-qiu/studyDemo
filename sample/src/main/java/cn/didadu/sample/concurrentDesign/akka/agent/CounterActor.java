package cn.didadu.sample.concurrentDesign.akka.agent;

import akka.actor.UntypedActor;
import akka.dispatch.Mapper;
import scala.concurrent.Future;

/**
 * Created by jinggg on 16/3/28.
 */
public class CounterActor extends UntypedActor{

    /**
     * 定义累加动作
     * 它的作用是对Agent的值进行修改
     */
    Mapper<Integer, Integer> addMapper = new Mapper<Integer, Integer>() {
        @Override public Integer apply(Integer parameter) {
            return parameter + 1;
        }
    };

    @Override public void onReceive(Object message) throws Exception {
        if(message instanceof Integer){
            for(int i = 0; i < 10000; i++){
                //我希望能知道Future何时结束
                Future<Integer> f = AgentDemo.counterAgent.alter(addMapper);
                /**
                 * 由于我们希望在将来知道累加行为是否完成，因此合理将返回的Future对象进行收集
                 */
                AgentDemo.futures.add(f);
            }
            getContext().stop(getSelf());
        }else {
            unhandled(message);
        }
    }
}
