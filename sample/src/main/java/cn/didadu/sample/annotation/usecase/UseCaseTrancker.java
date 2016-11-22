package cn.didadu.sample.annotation.usecase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * getDeclaredMethods()
   返回 Method 对象的一个数组，这些对象反映此 Class 对象表示的类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。
   getMethods()
   返回一个包含某些 Method 对象的数组，这些对象反映此 Class 对象所表示的类或接口（包括那些由该类或接口声明的以及从超类和超接口继承的那些的类或接口）的公共 member 方法。
 * @author zj
 *
 */
public class UseCaseTrancker {

	public static void trackUseCases(List<Integer> useCases,Class<?> cl){
		//循环PasswordUtils类中定义的方法
		for(Method m : cl.getDeclaredMethods()){
			//获取方法上的注解类
			UseCase uc = m.getAnnotation(UseCase.class);
			if(uc != null){
				//如果有注解则打印注解信息
				System.out.println("Found Use Case:" + uc.id() + " " + uc.description());
				useCases.remove(new Integer(uc.id()));
			}
		}
		
		for(int i : useCases){
			System.out.println("Waring: Missing use case-" + i);
		}
	}
	
	public static void main(String[] args){
		List<Integer> useCases = new ArrayList<Integer>();
		Collections.addAll(useCases, 47,48,49,50);
		trackUseCases(useCases,PasswordUtils.class);
	}
	
}
