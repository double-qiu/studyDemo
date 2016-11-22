package cn.didadu.sample.jvm.classLoading;

/**
 * Created by jinggg on 16/3/14.
 */
public class SubClass extends SuperClass{

    static {
        System.out.println("Sub class init!");
    }

}
