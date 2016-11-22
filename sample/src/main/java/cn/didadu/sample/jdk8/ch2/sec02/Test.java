package cn.didadu.sample.jdk8.ch2.sec02;

import cn.didadu.sample.jdk8.ch2.FilePath;

import java.io.*;
import java.math.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
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

    public static void main(String[] args) throws IOException {
        Path path = Paths.get(FilePath.ALICE_PATH);
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        /**
         * 通过Sring数组生成Stream
         */
        Stream<String> words = Stream.of(contents.split("[\\P{L}]+"));
        show("words", words);

        /**
         * 通过变长参数生成Stream
         */
        Stream<String> song = Stream.of("gently", "down", "the", "stream");
        show("song", song);

        /**
         * 生成空的Stream
         */
        Stream<String> silence = Stream.empty();
        silence = Stream.<String>empty(); // Explicit type specification
        show("silence", silence);

        /**
         * 创建一个含有常量的无限Stream
         */
        Stream<String> echos = Stream.generate(() -> "Echo");
        show("echos", echos);

        /**
         * 创建一个含有随机数的无限Stream
         */
        Stream<Double> randoms = Stream.generate(Math::random);
        show("randoms", randoms);

        /**
         * 创建一个有规则的无限Stream
         */
        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE, n -> n.add(BigInteger.ONE));
        show("integers", integers);

        /**
         * 通过正则表达式返回Stream
         */
        Stream<String> wordsAnotherWay = Pattern.compile("[\\P{L}]+").splitAsStream(contents);
        show("wordsAnotherWay", wordsAnotherWay);

        /**
         * 通过Files.lines方法放回一个包含文件中所有行的Stream
         * 为了确保关闭文件，最好使用java7中提供的try-with-resources
         * 当正常退出try语句块或抛出异常时，Stream与其关联底层文件都将被关闭
         */
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            show("lines", lines);
        }
    }
}
