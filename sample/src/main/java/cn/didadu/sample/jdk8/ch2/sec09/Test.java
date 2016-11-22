package cn.didadu.sample.jdk8.ch2.sec09;

import cn.didadu.sample.jdk8.ch2.FilePath;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Test {
    public static Stream<String> noVowels(String filename) throws IOException {
        String contents = new String(Files.readAllBytes(
                Paths.get(filename)), StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("[\\P{L}]+"));
        Stream<String> words = wordList.stream();
        return words.map(s -> s.replaceAll("[aeiouAEIOU]", ""));
    }

    public static <T> void show(String label, Set<T> set) {
        System.out.print(label + ": " + set.getClass().getName());
        System.out.println("[" +
                set.stream().limit(10).map(Object::toString).collect(Collectors.joining(", "))
                + "]");
    }

    public static void main(String[] args) throws IOException {

        /**
         * limit方法从无限的流中截取前10个
         * iterator将流生成传统的迭代器，以支持循环打印
         */
        Iterator<Integer> iter = Stream.iterate(0, n -> n + 1).limit(10).iterator();
        while (iter.hasNext())
            System.out.println(iter.next());

        /**
         * toArray返回的是Object类型的数组
         */
        Object[] numbers = Stream.iterate(0, n -> n + 1).limit(10).toArray();
        System.out.println(numbers); // Note it's an Object[] array

        /**
         * 可以将Object类型的数组中的其中一个元素强转成需要的类型
         * 不可以将Object类型的数组强转成需要的类型，会抛出异常
         */
        try {
            Integer number = (Integer) numbers[0]; // OK
            System.out.println("number: " + number);
            Integer[] numbers2 = (Integer[]) numbers; // Throws exception
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }

        /**
         * toArray接受Integer的构造函数，生成数组不再是Object类型，而是Integer类型
         */
        Integer[] numbers3 = Stream.iterate(0, n -> n + 1).limit(10).toArray(Integer[]::new);
        System.out.println(numbers3); // Note it's an Integer[] array

        /**
         * 自己实现从流中生成HashSet
         */
        HashSet<String> noVowelHashSet
                = noVowels(FilePath.ALICE_PATH).collect(HashSet::new, HashSet::add, HashSet::addAll);
        show("noVowelHashSet", noVowelHashSet);

        /**
         * 利用Collectors的toSet方法，从流中生成HashSet
         */
        Set<String> noVowelSet
                = noVowels(FilePath.ALICE_PATH).collect(Collectors.toSet());
        show("noVowelSet", noVowelSet);

        /**
         * 向(Collectors.toCollection方法中传递TreeSet构造函数，从流中生成TreeSet
         */
        TreeSet<String> noVowelTreeSet
                = noVowels(FilePath.ALICE_PATH).collect(Collectors.toCollection(TreeSet::new));
        show("noVowelTreeSet", noVowelTreeSet);

        /**
         * 将流中的前10个元素合并
         */
        String result = noVowels(FilePath.ALICE_PATH).limit(10).collect(Collectors.joining());
        System.out.println(result);

        /**
         * 将流中的前10个元素以逗号分割
         */
        result = noVowels(FilePath.ALICE_PATH).limit(10).collect(Collectors.joining(", "));
        System.out.println(result);

        /**
         * IntSummaryStatistics中可以获取最大值、最小值、个数、总数、平均值等
         */
        IntSummaryStatistics summary = noVowels(FilePath.ALICE_PATH).collect(
                Collectors.summarizingInt(String::length));
        double averageWordLength = summary.getAverage();
        double maxWordLength = summary.getMax();
        System.out.println("Average word length: " + averageWordLength);
        System.out.println("Max word length: " + maxWordLength);

        /**
         * forEach方法是终止操作，调用之后流就不可用了
         * 如果希望继续使用这个流，要使用peek方法
         */
        noVowels(FilePath.ALICE_PATH).limit(10).forEach(System.out::println);
    }
}


