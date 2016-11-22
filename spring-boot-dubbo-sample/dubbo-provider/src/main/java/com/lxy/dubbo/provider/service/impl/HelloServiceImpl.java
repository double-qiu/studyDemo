package com.lxy.dubbo.provider.service.impl;

import com.lxy.dubbo.api.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * Created by xiaoyi on 2016/7/18.
 */
@Service("helloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
