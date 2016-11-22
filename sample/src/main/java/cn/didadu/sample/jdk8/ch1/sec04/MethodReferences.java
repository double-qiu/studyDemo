package cn.didadu.sample.jdk8.ch1.sec04;

import java.util.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class MethodReferences extends Application {
   public void start(Stage stage) {
      /**
       * 方法引用示例，以下三种情况可以使用方法应用
       * 1、对象::示例方法  System.out::println 等同于 System.out.println(x)
       * 2、类::静态方法    Math::pow 等同于 (x,y) -> Math.pow(x,y)
       * 3、类::示例方法    String::compareToIgnoreCase 等同于 (x,y) -> x.compareToIgnoreCase(y)
       */
      String[] strings = "Mary had a little lamb".split(" ");
      Arrays.sort(strings, String::compareToIgnoreCase);
      System.out.println(Arrays.toString(strings));

      Button button = new Button("Click me!");
      button.setOnAction(System.out::println);
      stage.setScene(new Scene(button));
      stage.show();
   }
}
