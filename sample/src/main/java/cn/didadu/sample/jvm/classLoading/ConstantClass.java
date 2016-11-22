package cn.didadu.sample.jvm.classLoading;

/**
 * Created by jinggg on 16/3/14.
 */
public class ConstantClass {

    static {
        System.out.println("Constant class init!");
    }

    public static final String HELLOWORLD = "HELLO WORLD";

}
