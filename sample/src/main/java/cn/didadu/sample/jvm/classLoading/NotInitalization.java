package cn.didadu.sample.jvm.classLoading;

/**
 * Created by jinggg on 16/3/14.
 */
public class NotInitalization {

    public static void main(String[] args){
        //通过子类引用父类的静态字段，只会触发父类的初始化，不会触发子类的初始化
        // System.out.println(SubClass.value);

        //不会触发类初始化
        //SuperClass[] sca = new SuperClass[10];

        //在编译阶段通过常量传播优化，已经将字符串存储到常量池中，不会触发ConstantClass的初始化
        System.out.println(ConstantClass.HELLOWORLD);

    }

}
