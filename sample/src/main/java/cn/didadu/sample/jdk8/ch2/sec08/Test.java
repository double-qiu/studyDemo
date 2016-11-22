package cn.didadu.sample.jdk8.ch2.sec08;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Test {
    public static void main(String[] args) throws IOException {

        /**
         * 通过reduce将Stream中的数相加
         */
        Integer[] digits = {
                3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9, 3, 2, 3, 8, 4, 6, 2, 6, 4, 3, 3, 8, 3, 2, 7, 9, 5, 0, 2, 8, 8, 4, 1, 9, 7, 1, 6, 9, 3, 9, 9, 3, 7, 5, 1, 0, 5, 8, 2, 0, 9, 7, 4, 9, 4, 4, 5, 9, 2, 3, 0, 7, 8, 1, 6, 4, 0, 6, 2, 8, 6 };
        Stream<Integer> values = Stream.of(digits);
        Optional<Integer> sum = values.reduce((x, y) -> x + y);
        System.out.println("sum: " + sum);

        /**
         * 对空得Stream进行reduce操作将得到一个空得Optional
         */
        values = Stream.empty();
        sum = values.reduce((x, y) -> x + y); // Or values.reduce(Integer::sum);
        System.out.println("sum: " + sum);

        /**
         * 指定初始值对流进行reduce操作
         */
        values = Stream.of(digits);
        Integer sum2 = values.reduce(0, (x, y) -> x + y);
        System.out.println("sum2: " + sum2);

        /**
         * 初始值和空的Stream进行reduce操作，结果为0
         */
        values = Stream.empty();
        Integer sum3 = values.reduce(0, (x, y) -> x + y);
        System.out.println("sum3: " + sum3);

        /**
         * (s, w) -> s + w.length()会生成多个累加值
         * (s1, s2) -> s1 + s2)将累加值全部相加得到最后的值
         * 以下代码导致编译错误，不知道为啥
         */
    /*    String contents = new String(Files.readAllBytes(
                Paths.get(FilePath.ALICE_PATH)), StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("[\\P{L}]+"));
        Stream<String> words = wordList.stream();
        int result = words.reduce(0,
                (s, w) -> s + w.length(),
                (s1, s2) -> s1 + s2);

        System.out.println("result: " + result);*/
    }
}


