package cn.didadu.sample.jvm.classLoading;

/**
 * Created by jinggg on 16/3/14.
 */
public class SuperClass {

    static{
        System.out.println("Supper class init!");
    }

    public static int value = 123;

}
