package cn.didadu.sample.jdk8.ch2.sec12;

import cn.didadu.sample.jdk8.ch2.FilePath;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Test {
    public static void show(String title, IntStream stream) {
        final int SIZE = 10;
        int[] firstElements = stream.limit(SIZE + 1).toArray();
        System.out.print(title + ": [");
        int i;
        for (i = 0; i < SIZE && i < firstElements.length; i++) {
            System.out.print(firstElements[i]);
            if (i < firstElements.length - 1)
                System.out.print(", ");
        }
        if (i < firstElements.length)
            System.out.print("...");
        System.out.println("]");
    }

    public static void main(String[] args) throws IOException {
        /**
         * IntStream生成int原始类型的流
         */
        IntStream is1 = IntStream.generate(() -> (int) (Math.random() * 100));
        show("is1", is1);

        /**
         * range产生5-10连续的值，但是不包括上限
         */
        IntStream is2 = IntStream.range(5, 10);
        show("is2", is2);

        /**
         * rangeClosed产生5-10连续的值，包括上限
         */
        IntStream is3 = IntStream.rangeClosed(5, 10);
        show("is3", is3);

        Path path = Paths.get(FilePath.ALICE_PATH);
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        /**
         * mapToInt接受Lambda表达式，示例统计每个单词的长度
         */
        Stream<String> words = Stream.of(contents.split("[\\P{L}]+"));
        IntStream is4 = words.mapToInt(String::length);
        show("is4", is4);

        String sentence = "\uD835\uDD46 is the set of octonions.";
        System.out.println(sentence);
        IntStream codes = sentence.codePoints();
        System.out.println(codes.mapToObj(c -> String.format("%X ", c)).collect(Collectors.joining()));

        Stream<Integer> integers = IntStream.range(0, 100).boxed();
        IntStream is5 = integers.mapToInt(Integer::intValue);
        show("is5", is5);
    }
}


