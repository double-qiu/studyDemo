package cn.didadu.sample.concurrentDesign.akka.helloworld;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * Created by jinggg on 16/3/26.
 */
public class HelloMainSimple {

    public static void main(String[] args){
        /**
         * 创建ActorSystem，表示管理和维护Actor的系统
         * 一般来说一个应用程序只需要一个ActorSystem就够用了，两个参数分别为系统名称、配置文件
         */
        ActorSystem system = ActorSystem.create("Hello", ConfigFactory.load("samplehello.conf"));

        /**
         * 通过ActorSystem创建顶级Actor
         */
        ActorRef a = system.actorOf(Props.create(HelloWorld.class),"helloWorld");
        System.out.println("HelloWorld Actor Path:" + a.path());
    }

}
