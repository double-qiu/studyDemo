package cn.didadu.sample.jvm.virtualStack;


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;


/**
 * Method Handle 基础用法
 * Created by jinggg on 16/3/14.
 */
public class MethodHandleTest {

    static class ClassA{
        public void println(String s){
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable{
        Object obj = new ClassA();
        //无论obj属于哪个实现类，下面这句都能正确调用到pringln方法
        getPrintlnMH(obj).invokeExact("zhangjing");
    }

    public static MethodHandle getPrintlnMH(Object reveiver) throws Throwable{
        /**
         * MethodType:代表方法类型
         * 包含了方法的返回值和具体参数类型，分别为第一个参数和第二个参数
         */
        MethodType mt = MethodType.methodType(void.class,String.class);

        /**
         * lookup()方法源自于MethodHandles.lookup，这句的作用是在指定类中查找符合给定方法名称，方法类型，并且符合调用权限的方法句柄
         * 这里调用的是一个虚方法，按照Java语言的规范，方法的第一个参数是隐式的，代表该方法的接受者，也即是this指向的对象
         * 这个参数以前是放在参数列表中进行传递的，而现在提供了bindTo()方法来完成这件事
         */
        return MethodHandles.lookup().findVirtual(reveiver.getClass(),"println",mt).bindTo(reveiver);
    }
}
