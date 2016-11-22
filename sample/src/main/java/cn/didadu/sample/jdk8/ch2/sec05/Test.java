package cn.didadu.sample.jdk8.ch2.sec05;

import cn.didadu.sample.jdk8.ch2.FilePath;

import java.io.*;
import java.nio.charset.*;
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

    public static void main(String[] args) throws IOException {

        /**
         * distinct方法会过滤流中重复的元素，生成新的流
         */
        Stream<String> uniqueWords
                = Stream.of("merrily", "merrily", "merrily", "gently").distinct();
        show("uniqueWords", uniqueWords);

        /**
         * distinct方法会过滤流中重复的元素，生成新的流
         * sort方法会对原流中元素排序，生成新的已排序好得流
         */
        String contents = new String(Files.readAllBytes(
                Paths.get(FilePath.ALICE_PATH)), StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("[\\P{L}]+"));
        Stream<String> words = wordList.stream();
        show("words", words);

        words = wordList.stream();
        Stream<String> distinct = words.distinct();
        show("distinct", distinct);

        words = wordList.stream();
        Stream<String> sorted = words.sorted();
        show("sorted", sorted);

        words = wordList.stream();
        Stream<String> distinctSorted = words.distinct().sorted();
        show("distinctSorted", distinctSorted);

        /**
         * sorted方法接受Comparator对象，可以定制化排序
         * 示例是对字符串的长度逆序
         */
        words = wordList.stream();
        Stream<String> longestFirst = words.sorted(Comparator.comparing(String::length).reversed());
        show("longestFirst", longestFirst);
    }
}


