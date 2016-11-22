package cn.didadu.sample.jdk8.ch2.sec07;

import cn.didadu.sample.jdk8.ch2.FilePath;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Test {
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(
                Paths.get(FilePath.ALICE_PATH)), StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("[\\P{L}]+"));

        /**
         * findFirst寻找第一个含有red的单词
         * ifPresent判断如果optionalValue里含有值的话，执行Lambda表达式
         */
        Optional<String> optionalValue = wordList.stream().filter(s -> s.contains("red")).findFirst();
        optionalValue.ifPresent(s -> System.out.println(s + " contains red"));

        /**
         * ifPresent判断当值存在时将值添加到HashSet中
         */
        Set<String> results = new HashSet<>();
        optionalValue.ifPresent(results::add);
        Optional<Boolean> added = optionalValue.map(results::add);
        System.out.println(added);

        /**
         * orElse方法判断optionalValue是否有值
         * 如果有值则不做任何处理
         * 如果没有值则使用传递的默认值
         */
        optionalValue = wordList.stream().filter(s -> s.contains("fred")).findFirst();
        System.out.println(optionalValue.orElse("No word") + " contains fred");

        /**
         * orElse将没有值的optionalString替换成N/A
         */
        Optional<String> optionalString = Optional.empty();
        String result = optionalString.orElse("N/A");
        System.out.println("result: " + result);

        /**
         *orElseGet可以接受Lambda表达式来给Optional赋默认值
         */
        result = optionalString.orElseGet(() -> System.getProperty("user.dir"));
        System.out.println("result: " + result);

        /**
         * 当Optional值为空时，可以用orElseThrow抛出指定的异常
         */
        try {
            result = optionalString.orElseThrow(NoSuchElementException::new);
            System.out.println("result: " + result);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        /**
         * 使用flatMap来组合可选值
         */
        System.out.println(inverse(4.0).flatMap(Test::squareRoot));
        System.out.println(inverse(-1.0).flatMap(Test::squareRoot));
        System.out.println(inverse(0.0).flatMap(Test::squareRoot));
        Optional<Double> result2 = Optional.of(-4.0).flatMap(Test::inverse).flatMap(Test::squareRoot);
        System.out.println(result2);
    }

    /**
     * 创建可选值，若x为0返回空，否则返回可选值
     * @param x
     * @return
     */
    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}


