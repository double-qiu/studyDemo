package cn.didadu.sample.jdk8.ch5.sec02;

import java.time.*;
import java.time.temporal.*;

public class LocalDates {
    public static void main(String[] args) {

        /**
         * 本地时间，不包含时区信息
         * 默认yyyy-MM-dd格式
         */
        LocalDate today = LocalDate.now(); // Today’s date
        System.out.println("today: " + today);

        /**
         * 通过LocalDate.of构造一个确定的本地时间
         */
        LocalDate alonzosBirthday = LocalDate.of(1903, 6, 14);
        alonzosBirthday = LocalDate.of(1903, Month.JUNE, 14);
        // Uses the Month enumeration
        System.out.println("alonzosBirthday: " + alonzosBirthday);

        /**
         * 获取日期的后多少天的年月日
         */
        LocalDate programmersDay = LocalDate.of(2014, 1, 1).plusDays(255);
        // September 13, but in a leap year it would be September 12
        System.out.println("programmersDay: " + programmersDay);

        /**
         * 返回两个日期之间的距离
         * independenceDay.until(christmas))返回5个月21天，因为每个月的天数都在变，不实用
         * independenceDay.until(christmas, ChronoUnit.DAYS))返回天数
         */
        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        LocalDate christmas = LocalDate.of(2014, Month.DECEMBER, 25);
        System.out.println("Until christmas: " + independenceDay.until(christmas));
        System.out.println("Until christmas: " + independenceDay.until(christmas, ChronoUnit.DAYS));

        /**
         * 产生了不存在的日期，但是api会给出实际存在的正确的日期
         */
        System.out.println(LocalDate.of(2016, 1, 31).plusMonths(1));
        System.out.println(LocalDate.of(2016, 3, 31).minusMonths(1));

        /**
         * 获取星期相关函数
         */
        DayOfWeek startOfLastMillennium = LocalDate.of(1900, 1, 1).getDayOfWeek();
        System.out.println("startOfLastMillennium: " + startOfLastMillennium);
        System.out.println(startOfLastMillennium.getValue());
        System.out.println(DayOfWeek.SATURDAY.plus(3));
    }
}
