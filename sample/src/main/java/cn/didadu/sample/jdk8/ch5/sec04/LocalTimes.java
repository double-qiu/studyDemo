package cn.didadu.sample.jdk8.ch5.sec04;

import java.time.*;

public class LocalTimes {
    public static void main(String[] args) {

        /**
         * 获取现在的时间
         * 获取固定时间
         */
        LocalTime rightNow = LocalTime.now();
        LocalTime bedtime = LocalTime.of(22, 30);
        bedtime = LocalTime.of(22, 30, 0);

        System.out.println("rightNow: " + rightNow);
        System.out.println("bedtime: " + bedtime);

        /**
         * 获取多少小时后的时间
         */
        LocalTime wakeup = bedtime.plusHours(8); // wakeup is 6:30
        System.out.println("wakeup: " + wakeup);
    }
}
