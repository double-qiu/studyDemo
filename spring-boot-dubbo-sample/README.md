#spring-boot-dubbo-sample

##简介
此小程序展示了如何用spring-boot启动一个dubbo provider，并通过spring-boot启动一个consumer。本程序，下载后无需修改可以直接运行
Spring-boot启动Dubbo时，无需启动tomcat等容器，即非web项目，那么需要在springApplication的main中设置web(false)即可

##运行方法
1. maven引入相关包后，运行provider中的App中的main，即可启动provider
2. 启动provider后，运行consumer中的App的main，即读取provider中的helloService


##联系方式：
站内信吧

##其他spring-boot演示程序
1. [通过Spring-boot整合mybatis同时连接2个数据库](http://git.oschina.net/smarti/spring-boot-mybatis-sample)