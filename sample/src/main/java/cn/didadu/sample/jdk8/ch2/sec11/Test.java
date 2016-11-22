package cn.didadu.sample.jdk8.ch2.sec11;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;

class City {
   private String name;
   private String state;
   private int population;

   public City(String name, String state, int population) {
      this.name = name;
      this.state = state;
      this.population = population;
   }

   public String getName() { return name; }
   public String getState() { return state; }
   public int getPopulation() { return population; }
}

public class Test {
   public static Stream<City> readCities(String filename) throws IOException {
      return Files.lines(Paths.get(filename)).map(l -> l.split(", ")).map(a -> new City(a[0], a[1], Integer.parseInt(a[2]))); 
   }

   public static void main(String[] args) throws IOException {

      /**
       * groupingBy会按照条件自动分组
       */
      Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
      Map<String, List<Locale>> countryToLocales = locales.collect(
         Collectors.groupingBy(Locale::getCountry));
      System.out.println("Swiss locales: " + countryToLocales.get("CH"));

      /**
       * partitioningBy会把流分成true或false两组列表
       */
      locales = Stream.of(Locale.getAvailableLocales());
      Map<Boolean, List<Locale>> englishAndOtherLocales = locales.collect(
         Collectors.partitioningBy(l -> l.getLanguage().equals("en")));
      System.out.println("English locales: " + englishAndOtherLocales.get(true));

      /**
       * toSet()下游收集器对分组后的元素进行处理，返回Set格式
       */
      locales = Stream.of(Locale.getAvailableLocales());
      Map<String, Set<Locale>> countryToLocaleSet = locales.collect(
         groupingBy(Locale::getCountry, toSet()));
      System.out.println("countryToLocaleSet: " + countryToLocaleSet);

      /**
       * counting()下游收集器对分组后的元素进行处理，返回每一组的数量
       */
      locales = Stream.of(Locale.getAvailableLocales());
      Map<String, Long> countryToLocaleCounts = locales.collect(
              groupingBy(Locale::getCountry, counting()));
      System.out.println("countryToLocaleCounts: " + countryToLocaleCounts);

      /**
       * groupingBy按州分组，summingInt下游收集器计算出所有城市的人口数
       */
      Stream<City> cities = readCities("/Users/admin/IdeaProjects/sample/src/main/java/cn/didadu/sample/jdk8/ch2/sec11/cities.txt");
      Map<String, Integer> stateToCityPopulation = cities.collect(
         groupingBy(City::getState, summingInt(City::getPopulation)));
      System.out.println("stateToCityPopulation: " + stateToCityPopulation);

      /**
       * groupingBy按州分组
       * maxBy获取每个州下城市名称最长的城市
       * mapping方法会将一个函数应用到下游收集器结果上，并且需要另一个收集器来处理结果
       */
      cities = readCities("/Users/admin/IdeaProjects/sample/src/main/java/cn/didadu/sample/jdk8/ch2/sec11/cities.txt");
      Map<String, Optional<String>> stateToLongestCityName = cities.collect(
         groupingBy(City::getState, 
            mapping(City::getName,
               maxBy(Comparator.comparing(String::length)))));
      System.out.println("stateToLongestCityName: " + stateToLongestCityName);

      locales = Stream.of(Locale.getAvailableLocales());
      Map<String, Set<String>> countryToLanguages = locales.collect(
         groupingBy(Locale::getDisplayCountry, 
            mapping(Locale::getDisplayLanguage,
               toSet())));
      System.out.println("countryToLanguages: " + countryToLanguages);

      /**
       * groupingBy按州分组
       * summarizingInt获取州下面城市人数的最大值、最小值、平均值、总数等等
       */
      cities = readCities("/Users/admin/IdeaProjects/sample/src/main/java/cn/didadu/sample/jdk8/ch2/sec11/cities.txt");
      Map<String, IntSummaryStatistics> stateToCityPopulationSummary = cities.collect(
         groupingBy(City::getState,
            summarizingInt(City::getPopulation)));
      System.out.println(stateToCityPopulationSummary.get("NY"));

      /**
       * 以下两个方法都是将州下地城市以逗号分隔开
       * 推荐使用第二种方法
       */
      cities = readCities("/Users/admin/IdeaProjects/sample/src/main/java/cn/didadu/sample/jdk8/ch2/sec11/cities.txt");
      Map<String, String> stateToCityNames = cities.collect(
         groupingBy(City::getState,
            reducing("", City::getName,
               (s, t) -> s.length() == 0 ? t : s + ", " + t)));

      cities = readCities("/Users/admin/IdeaProjects/sample/src/main/java/cn/didadu/sample/jdk8/ch2/sec11/cities.txt");
      stateToCityNames = cities.collect(
         groupingBy(City::getState,
            mapping(City::getName,
               joining(", "))));
      System.out.println("stateToCityNames: " + stateToCityNames);
   }
}


