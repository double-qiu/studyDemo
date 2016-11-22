package cn.didadu.sample.jvm.virtualStack;

/**
 * Created by jinggg on 16/3/14.
 */
public class StaticDispatch {

    static abstract class Human{

    }

    static class Man extends Human{

    }

    static class Women extends Human{

    }

    public void sayHello(Human man){
        System.out.println("hello,human");
    }

    public void sayHello(Man man){
        System.out.println("hello,man");
    }

    public void sayHello(Women women){
        System.out.println("hello,woman");

    }

    public static void main(String[] args){

        /**
         * Human称为静态类型
         * Man称为实例类型
         * 虚拟机在重载时是通过静态类型判定的，所以选择了Human版本的重载方法
         * 所有依赖静态类型来定位方法执行的分派动作称为静态分配
         */
        Human man = new Man();
        Human women = new Women();

        StaticDispatch sd = new StaticDispatch();
        sd.sayHello(man);
        sd.sayHello(women);
    }

}
