package cn.didadu.sample.jvm.sugar;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zhangjing on 2015/9/7.
 * Java的泛型是伪泛型,在编译期间，所有的泛型信息都会被擦除掉
 * 如在代码中定义的List<object>和List<String>等类型，在编译后都会编程List
 */
public class GenericTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        /**
         * 示例1
         */
        ArrayList<String> arrayList1=new ArrayList<String>();
        arrayList1.add("abc");
        ArrayList<Integer> arrayList2=new ArrayList<Integer>();
        arrayList2.add(123);
        //输出true，说明泛型类型String和Integer都被擦除掉了，只剩下了原始类型
        System.out.println(arrayList1.getClass()==arrayList2.getClass());

        /**
         * 示例2
         * ArrayList泛型类型实例化为Integer的对象，如果直接调用add方法，那么只能存储整形的数据
         * 当我们利用反射调用add方法的时候，却可以存储字符串
         * 这说明了Integer泛型实例在编译之后被擦除了，只保留了原始类型
         */
        ArrayList<Integer> arrayList3=new ArrayList<Integer>();
        arrayList3.add(1);//这样调用add方法只能存储整形，因为泛型类型的实例为Integer
        arrayList3.getClass().getMethod("add", Object.class).invoke(arrayList3, "asd");
        for (int i=0;i<arrayList3.size();i++) {
            System.out.println(arrayList3.get(i));
        }

        /**
         * 示例3
         * 在调用泛型方法的时候，可以指定泛型，也可以不指定泛型。
         * 在不指定泛型的情况下，泛型变量的类型为 该方法中的几种类型的同一个父类的最小级，直到Object
         * 在指定泛型的时候，该方法中的几种类型必须是该泛型实例类型或者其子类
         */
        /**不指定泛型的时候*/
        int i=GenericTest.add(1, 2); //这两个参数都是Integer，所以T为Integer类型
        Number f=GenericTest.add(1, 1.2);//这两个参数一个是Integer，以风格是Float，所以取同一父类的最小级，为Number
        Object o=GenericTest.add(1, "asd");//这两个参数一个是Integer，以风格是Float，所以取同一父类的最小级，为Object
        /**指定泛型的时候*/
        int a=GenericTest.<Integer>add(1, 2);//指定了Integer，所以只能为Integer类型或者其子类
        //int b=GenericTest.<Integer>add(1, 2.2);//编译错误，指定了Integer，不能为Float
        Number c=GenericTest.<Number>add(1, 2.2); //指定为Number，所以可以为Integer和Float


        /**
         * 示例4
         * ArrayList中，如果不指定泛型，那么这个ArrayList中可以放任意类型的对象
         */
        ArrayList arrayList=new ArrayList();
        arrayList.add(1);
        arrayList.add("121");
        arrayList.add(new Date());
    }


    //这是一个简单的泛型方法
    public static <T> T add(T x,T y){
        return y;
    }

    /**
     * 一下两个方法无法重载，应为泛型类型擦除
     * @param list
     * @return
     */
/*    public static String method(List<String> list){
        System.out.println("invoke method(List<String> list)");
        return "";
    }

    public static String method(List<Integer> list){
        System.out.println("invoke method(List<Integer> list)");
        return "";
    }*/
}
