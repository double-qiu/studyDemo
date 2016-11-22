package cn.didadu.sample.jvm.classLoading;

/**
 * Created by jinggg on 16/3/14.
 */
public class InitalizationTest {

    static {
        //可以访问后面定义的静态变量
        i = 0;
        //错误：非法向前引用
        //System.out.println(i);
    }

    static int i = 0;
}
