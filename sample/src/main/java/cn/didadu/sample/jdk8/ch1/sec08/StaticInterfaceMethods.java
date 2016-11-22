package cn.didadu.sample.jdk8.ch1.sec08;

public class StaticInterfaceMethods {
    public static void main(String[] args) {
        Greeter worldGreeter = Greeter.newInstance("World");
        System.out.println(worldGreeter.greet());
    }
}

interface Greeter {

    String greet();

    static Greeter newInstance(String greeted) {

        /**
         * Lambda表达方式
         * 因为Greeter只有一个抽象方法
         * return中返回的相当于一个实现了greet方法的Greeter实例
         */
        return () -> "Hello, " + greeted;

        //旧的表达方式
       /* return new Greeter() {
            public String greet() {
                return "Hello, " + greeted;
            }
        };*/
    }
}
