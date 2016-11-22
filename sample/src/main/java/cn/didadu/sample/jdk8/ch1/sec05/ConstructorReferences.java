package cn.didadu.sample.jdk8.ch1.sec05;

import java.util.*;
import java.util.stream.*;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class ConstructorReferences extends Application {


    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage) {
        /**
         * 构造器引用
         * Button::new通过List<String> labels推导出调用哪个构造函数
         */
        List<String> labels = Arrays.asList("Ok", "Cancel", "Yes", "No", "Maybe");
        Stream<Button> stream = labels.stream().map(Button::new);
        List<Button> buttons = stream.collect(Collectors.toList());
        System.out.println(buttons);


        stream = labels.stream().map(Button::new);
        Object[] buttons2 = stream.toArray();
        System.out.println(buttons2.getClass());

        // The following generates a ClassCastException
        // stream = labels.stream().map(Button::new);
        // Button[] buttons3 = (Button[]) stream.toArray();

        stream = labels.stream().map(Button::new);
        Button[] buttons4 = stream.toArray(Button[]::new);

        final double rem = Font.getDefault().getSize();
        HBox box = new HBox(0.8 * rem);
        box.getChildren().addAll(buttons4);
        stage.setScene(new Scene(box));
        stage.show();
    }
}
