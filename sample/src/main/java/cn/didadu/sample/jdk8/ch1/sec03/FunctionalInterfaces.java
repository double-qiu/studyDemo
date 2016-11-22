package cn.didadu.sample.jdk8.ch1.sec03;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class FunctionalInterfaces extends Application {

    public static void main(String[] args) {

        /**
         * Lambda形式的自定义比较器示例
         */
        String[] strings = "Mary had a little lamb".split(" ");
        Arrays.sort(strings,
                (first, second) -> Integer.compare(first.length(), second.length()));
        System.out.println(Arrays.toString(strings));

        /**
         * Lambda形式的按钮回调示例
         * 该类继承自Application，launch方法中会调用start方法
         */
        launch(args);
    }

    public void start(Stage stage) {
        Button button = new Button("Click me!");
        button.setOnAction(event -> System.out.println("Thanks for clicking!"));

        stage.setScene(new Scene(button));
        stage.show();
    }

    public void sample(){
        /**
         * Arrays.sort方法接收的参数是Comparator，不是函数式接口
         * 所以Arrays.sort(strings, comp)无法通过编译
         */
        BiFunction<String, String, Integer> comp
                = (first, second) -> Integer.compare(first.length(), second.length());
        // Arrays.sort(strings, comp);
        // Error: Arrays.sort doesn't want a BiFunction

        // Runnable sleeper = () -> { System.out.println("Zzz"); Thread.sleep(1000); };
        // Error: Thread.sleep can throw a checked InterruptedException

        /**
         * Lambda表达式中如果需要抛出异常，该异常需要再目标接口的抽象方法中声明
         * Runnable的run方法没有声明异常，所以必须在Lambda表达式中捕获异常
         */
        Runnable sleeper2 = () -> {
            System.out.println("Zzz");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        };

        /**
         * Callable的call方法可以抛出异常，Lambda表达式结尾需要返回null，否则编译不通过
         */
        Callable<Void> sleeper3 = () -> {
            System.out.println("Zzz");
            Thread.sleep(1000);
            return null;
        };
    }
}
