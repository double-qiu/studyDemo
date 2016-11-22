package cn.didadu.sample.jdk8.ch1.sec06;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class VariableScope {
    public static void main(String[] args) {
        repeatMessage("Hello", 100);
    }

    /**
     * Lambda中保存了text、count的值
     * @param text
     * @param count
     */
    public static void repeatMessage(String text, int count) {
        Runnable r = () -> {
            for (int i = 0; i < count; i++) {
                System.out.println(text);
                Thread.yield();
            }
        };
        new Thread(r).start();
    }

    /**
     * Lambda中不可以更改count变量
     * @param text
     * @param count
     */
    public static void repeatMessage2(String text, int count) {
        Runnable r = () -> {
            while (count > 0) {
                // count--; // Error: Can't mutate captured variable
                System.out.println(text);
                Thread.yield();
            }
        };
        new Thread(r).start();
    }

    /**
     * Lambda中不可以更改局部变量matches的值
     * @param dir
     * @param word
     * @throws IOException
     */
    public static void countMatches(Path dir, String word) throws IOException {
        Path[] files = getDescendants(dir);
        int matches = 0;
        for (Path p : files)
            new Thread(() -> {
                if (contains(p, word)) {
                    // matches++;
                    // ERROR: Illegal to mutate matches
                }
            }).start();
    }

    /**
     * Lambda中更改成员变量是合法的，但是这样做不恰当
     * matches++不是原子操作，如果多线程访问会导致线程安全问题
     */
    private static int matches;
    public static void countMatches2(Path dir, String word) {
        Path[] files = getDescendants(dir);
        for (Path p : files)
            new Thread(() -> {
                if (contains(p, word)) {
                    matches++;
                    // CAUTION: Legal to mutate matches, but not threadsafe
                }
            }).start();
    }

    /**
     * Lambda中更改共享对象是合法的
     * matches是有效final的，是指变量被初始化后不会再赋予一个新的对象，但是可以改变对象里地内容
     * 同样是线程不安全的
     * @param dir
     * @param word
     * @return
     */
    // Warning: Bad code ahead
    public static List<Path> collectMatches(Path dir, String word) {
        Path[] files = getDescendants(dir);
        List<Path> matches = new ArrayList<>();
        for (Path p : files)
            new Thread(() -> {
                if (contains(p, word)) {
                    matches.add(p);
                    // CAUTION: Legal to mutate matches, but not threadsafe
                }
            }).start();
        return matches;
    }

    public static Path[] getDescendants(Path dir) {
        try {
            try (Stream<Path> entries = Files.walk(dir)) {
                return entries.toArray(Path[]::new);
            }
        } catch (IOException ex) {
            return new Path[0];
        }
    }

    public static boolean contains(Path p, String word) {
        try {
            return new String(Files.readAllBytes(p),
                    StandardCharsets.UTF_8).contains(word);
        } catch (IOException ex) {
            return false;
        }
    }
}
