package cn.didadu.sample.jdk8.ch7.sec02;

import java.io.*;
import java.nio.file.*;
import javafx.application.*;
import javafx.stage.*;
import javax.script.*;

public class Test3 extends Application {
   private static ScriptEngineManager manager = new ScriptEngineManager();
   private static ScriptEngine engine = manager.getEngineByName("nashorn");

   public void start(Stage stage) {
      Bindings scope = engine.createBindings();
      scope.put("stage", stage);
      try {
         engine.eval(Files.newBufferedReader(Paths.get("/Users/admin/IdeaProjects/sample/src/main/java/cn/didadu/sample/jdk8/ch7/sec02/hellofx.js")), scope);
         // Script code can access the object as stage
      } catch (IOException | ScriptException ex) {
         ex.printStackTrace();
      }
   }
}
