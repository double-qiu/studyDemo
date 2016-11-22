package cn.didadu.sample.jdk8.ch2.sec06;

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

      Stream<String> words = wordList.stream();

      /**
       * compareToIgnoreCase获取最大值
       * 因为流有可能为空，所以返回Optional对象
       * Optional是新的api处理null的方式
       */
      Optional<String> largest = words.max(String::compareToIgnoreCase);
      if (largest.isPresent())
         System.out.println("largest: " + largest.get());

      /**
       * anyMatch是查找是否含有任何一个亿Q开头的单词
       * 不用遍历整个流，一点找到，立即返回
       */
      words = wordList.stream();
      boolean aWordStartsWithQ
         = words.anyMatch(s -> s.startsWith("Q"));
      System.out.println("aWordStartsWithQ: " + aWordStartsWithQ);

      /**
       * findAny是查找一个含有Q开头的单词，找到立即返回
       */
      words = wordList.stream();
      Optional<String> startsWithQ = words.parallel().filter(s -> s.startsWith("Q")).findAny();
      if (startsWithQ.isPresent())
         System.out.println("startsWithQ: " + startsWithQ.get());
      else
         System.out.println("No word starts with Q");
   }
}


