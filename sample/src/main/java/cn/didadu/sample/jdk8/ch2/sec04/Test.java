package cn.didadu.sample.jdk8.ch2.sec04;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Test {
    public static <T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        List<T> firstElements = stream.limit(SIZE + 1).collect(Collectors.toList());
        System.out.print(title + ": ");
        if (firstElements.size() <= SIZE)
            System.out.println(firstElements);
        else {
            firstElements.remove(SIZE);
            String out = firstElements.toString();
            System.out.println(out.substring(0, out.length() - 1) + ", ...]");
        }
    }

    public static Stream<Character> characterStream(String s) {
        List<Character> result = new ArrayList<>();
        for (char c : s.toCharArray())
            result.add(c);
        return result.stream();
    }

    public static void main(String[] args) throws IOException {
        /**
         * limit方法用于截取流，取前100个元素生成新的流返回
         */
        Stream<Double> randoms = Stream.generate(Math::random).limit(100);
        show("randoms", randoms);

        /**
         * integers是个无限流，通过limit方法截取了前5个元素
         */
        Stream<Integer> integers = Stream.iterate(0, n -> n + 1);
        Stream<Integer> firstFive = integers.limit(5);
        show("firstFive", firstFive);

        /**
         * skip方法跳过流中的第一个元素
         */
        integers = Stream.iterate(0, n -> n + 1);
        Stream<Integer> notTheFirst = integers.skip(1);
        show("notTheFirst", notTheFirst);

        /**
         * concat方法可以连接两个流，但是第一个流不能是一个无限流
         */
        Stream<Character> combined = Stream.concat(
                characterStream("Hello"),
                characterStream("World"));
        show("combined", combined);

        /**
         *peek方法会产生另一个与原始流具有相同个元素的流
         * 但是每次获取时会调用一个函数，这样可以用于调试惰性取值方法
         */
        Object[] powers = Stream.iterate(1.0, p -> p * 2)
                .peek(e -> System.out.println("Fetching " + e))
                .limit(20).toArray();
        System.out.println(Arrays.asList(powers));
    }
}


