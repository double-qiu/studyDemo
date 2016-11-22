package cn.didadu.sample.concurrentDesign;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference可以解决ABA问题，但是依然存在时间跨度比较长的ABA问题
 * 以下示例说明了时间跨度长，依然存在ABA问题，关键是timestamp获取的时间点
 * Created by jinggg on 16/3/22.
 */
public class AtomicStampedReferenceDemo {

    public static AtomicStampedReference<Integer> money = new AtomicStampedReference<Integer>(19,0);

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 3; i++){
            if(i == 1){
                new Thread(() -> {
                    for(int j = 0; j < 2; j++){
                        while (true){
                            int timestamp = money.getStamp();
                            Integer m = money.getReference();
                            if(m > 10){
                                System.out.println("大于10元");
                                if(money.compareAndSet(m, m - 10, timestamp, timestamp + 1)){
                                    System.out.println("成功消费10元，余额：" + money.getReference() + "元！Timestamp=" + money.getStamp());
                                    break;
                                }
                            }else{
                                System.out.println("没有足够余额！");
                                break;
                            }
                        }
                    }
                }).start();
                Thread.sleep(5000);
            }
            final int timestamp = money.getStamp();
            System.out.println(timestamp);
            new Thread(() -> {
                while(true){
                    Integer m = money.getReference();
                    if(m < 20){
                        if(money.compareAndSet(m, m + 20, timestamp, timestamp + 1)){
                            System.out.println("余额小于20元，充值成功，余额：" + money.getReference() + "元！Timestamp=" + money.getStamp());
                            break;
                        }
                    }else{
                        break;
                    }
                }
            }).start();
        }




    }


}
