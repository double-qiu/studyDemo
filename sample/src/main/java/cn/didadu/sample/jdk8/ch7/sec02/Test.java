package cn.didadu.sample.jdk8.ch7.sec02;

import java.io.*;
import java.nio.file.*;
import javax.script.*;

public class Test {
   public static void main(String[] args) throws ScriptException, IOException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine = manager.getEngineByName("nashorn");
      Object result = engine.eval("'Hello, World!'.length");
      System.out.println(result);
      result = engine.eval(Files.newBufferedReader(Paths.get("/Users/admin/IdeaProjects/sample/src/main/java/cn/didadu/sample/jdk8/ch7/sec02/hello.js")));
      System.out.println(result);
   }
}
