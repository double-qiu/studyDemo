package com.spring.redis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.spring.redis.impl.GetUserImpl;
import com.spring.redis.vo.UserVO;

public class TestMain {
   
	private static ApplicationContext act;

	public static void main(String[] args) throws InterruptedException {
		String [] iem = {"classpath:application-aop.xml","classpath:application-propertyFile.xml","classpath:application-redis.xml"};
		act = new FileSystemXmlApplicationContext(iem); 
		
	    Map<String, UserVO> maps = new HashMap<String, UserVO>();
		maps.put("222", new UserVO("222", "lisi", "男", 21, "15648921562"));
		maps.put("333", new UserVO("333", "zhangsan", "女", 24, "154892614789"));
		maps.put("444", new UserVO("444", "mito", "男", 22, "12364596122"));
		maps.put("555", new UserVO("555", "yes", "女", 40, "15962351489"));
		
		GetUserImpl.init = maps ;
		
		IGetUser g=(IGetUser) act.getBean("getUserServer"); 
		//测试 查询
		/*UserVO vo = g.get("555");
		System.out.println(vo.getIphone()+"------");*/
		
	 //测试删除
	/*	System.out.println(g.delete("777"));
		//验证删除成功否
		System.out.println(g.get("777"));
		System.out.println(GetUserImpl.init.size());*/
		
		//测试更新
	/*	UserVO uvo = g.update("888", new UserVO("888", "更新", "男", 32, "987654321"));
		System.out.println(uvo.getName());
		//验证更新是否成功
		System.out.println(g.get("888").getIphone());*/
		
		//测试添加
		UserVO avo = g.add("999", new UserVO("999","qiu","男",26,"111111"));
		System.out.println(avo.getName());
		//验证是否添加成功
		System.out.println(g.get("999").getIphone());
		System.out.println(GetUserImpl.init.size());
	}
}
