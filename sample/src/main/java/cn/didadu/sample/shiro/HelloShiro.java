package cn.didadu.sample.shiro;

import org.apache.log4j.spi.LoggerFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;

/**
 * Created by jinggg on 16/3/22.
 */
public class HelloShiro {

    private static final Logger loger = org.slf4j.LoggerFactory.getLogger(HelloShiro.class);

    public static void main(String[] args){
        //初始化SecurityManager
        /**
         * 读取classpath下的shiro.ini配置文件
         * 通过工厂类创建SecurityManager对象
         * 将其放入SecurityUtils中，供Shiro框架随时使用
         */
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager =  factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //获取当前用户
        /**
         * 通过SecurityUtils获取Subject对象，就是当前用户
         */
        Subject subject = SecurityUtils.getSubject();

        //登录
        /**
         * 身份验证是否是当前用户
         */
        UsernamePasswordToken token = new UsernamePasswordToken("shiro","201314");
        try{
            subject.login(token);
        }catch (AuthenticationException e){
            loger.info("登录失败");
            return;
        }

        loger.info("登录成功 " + subject.getPrincipal());
        subject.logout();
    }

}
