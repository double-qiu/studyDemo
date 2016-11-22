package cn.didadu.sample.jdk8.ch2.sec10;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class Person {
    private int id;
    private String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getClass().getName() +
                "[id=" + id + ",name=" + name + "]";
    }
}

public class Test {
    public static Stream<Person> people() {
        return Stream.of(
                new Person(1001, "Peter"),
                new Person(1002, "Paul"),
                new Person(1003, "Mary"));
    }

    public static Stream<Person> DuplicateKeyPeople() {
        return Stream.of(
                new Person(1001, "Peter"),
                new Person(1002, "Paul"),
                new Person(1003, "Mary"));
                //new Person(1002, "Mary"));
    }

    public static void main(String[] args) throws IOException {

        /**
         * 通过Collectors.toMap构造key-value形式的Map结构
         */
        Map<Integer, String> idToName = people().collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println("idToName: " + idToName);

        /**
         * Function.identity()函数返回实际的元素本身
         */
        Map<Integer, Person> idToPerson = people().collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println("idToPerson: " + idToPerson.getClass().getName() + idToPerson);

        /**
         * 当key重复时，toMap的第三个参数可以指定Lambda表达式
         */
        idToPerson = DuplicateKeyPeople().collect(
                Collectors.toMap(
                        Person::getId,
                        Function.identity(),
                        (existingValue, newValue) -> {
                            throw new IllegalStateException();
                        },
                        TreeMap::new));
        System.out.println("idToPerson: " + idToPerson.getClass().getName() + idToPerson);

        /**
         * (existingValue, newValue) -> existingValue));如果有存在的key，保留一个
         */
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, String> languageNames = locales.collect(
                Collectors.toMap(
                        Locale::getDisplayLanguage,
                        Locale::getDisplayLanguage,
                        (existingValue, newValue) -> existingValue));
        System.out.println("languageNames: " + languageNames);

        /**
         * 将含有同样key的值组合起来
         */
        locales = Stream.of(Locale.getAvailableLocales());
       /* Map<String, Set<String>> countryLanguageSets = locales.collect(
                Collectors.toMap(
                        Locale::getDisplayCountry,
                        l -> Collections.singleton(l.getDisplayLanguage()),
                        (a, b) -> { // union of a and b
                            Set<String> r = new HashSet<>(a);
                            r.addAll(b);
                            return r;
                        }));*/
//        System.out.println("countryLanguageSets: " + countryLanguageSets);
    }
}


