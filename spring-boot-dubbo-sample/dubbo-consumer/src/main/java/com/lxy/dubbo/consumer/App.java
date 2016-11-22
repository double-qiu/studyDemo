package com.lxy.dubbo.consumer;

import com.lxy.dubbo.api.service.HelloService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiaoyi on 2016/7/19.
 */
@SpringBootApplication
public class App {

    public static void main(String[] args){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-dubbo.xml"});
        HelloService helloService = (HelloService) ctx.getBean("helloService");
        System.out.println(helloService.sayHello("lxy"));
        while(true);
    }
}
