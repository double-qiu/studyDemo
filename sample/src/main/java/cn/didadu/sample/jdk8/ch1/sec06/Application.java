package cn.didadu.sample.jdk8.ch1.sec06;

import java.nio.file.*;
import java.util.*;

public class Application {
   public static void main(String[] args) {

      /**
       * Lambda中的first变量名与Path类型的first变量名重名，无法通过编译
       */
      // Path first = Paths.get("/usr/bin");
      // Uncomment to see error "variable first is already defined"
      // in the lambda expression below
      Comparator<String> comp = 
         (first, second) -> Integer.compare(first.length(), second.length());
      Application app = new Application();
      app.doWork();
   }

   /**
    * this关键字引用的是创建该Lambda表达式的对象，即Application
    */
   public void doWork() {      
      Runnable runner = () -> { System.out.println(this.toString()); };
      runner.run();
      // Prints Application@... since this refers to an Application object
   }
}
