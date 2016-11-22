package cn.didadu.sample.jvm.virtualStack;

/**
 * Created by jinggg on 16/3/14.
 */
public class DynamicDispatch {

    static abstract class Human{
        protected abstract void sayHello();
    }

    static class Man extends Human{
        @Override protected void sayHello() {
            System.out.println("hello,man");
        }
    }

    static class Woman extends Human{
        @Override protected void sayHello() {
            System.out.println("hello,woman");
        }
    }

    public static void main(String[] args){
        Human man = new Man();
        Human woman = new Woman();
        /**
         * 我们把在运行期根据实际类型确定方法执行版本的分配过程称为动态分配
         */
        man.sayHello();
        woman.sayHello();
        man = new Woman();
        man.sayHello();
    }
}
