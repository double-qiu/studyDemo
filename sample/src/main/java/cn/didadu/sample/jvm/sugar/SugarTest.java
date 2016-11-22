package cn.didadu.sample.jvm.sugar;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jinggg on 16/3/15.
 */
public class SugarTest {
    public static void main(String[] args) {
       /* List<Integer> list = Arrays.asList(1, 2, 3, 4);
        // 如果在JDK 1.7中，还有另外一颗语法糖 ，
        // 能让上面这句代码进一步简写成List<Integer> list = [1, 2, 3, 4];
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        System.out.println(sum);*/

        List list = Arrays.asList( new Integer[] {
                Integer.valueOf(1),
                Integer.valueOf(2),
                Integer.valueOf(3),
                Integer.valueOf(4) });

        int sum = 0;
        for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) {
            int i = ((Integer)localIterator.next()).intValue();
            sum += i;
        }
        System.out.println(sum);

        /**
         * ==号运算在不遇到算数运算的情况下不会自动拆箱
         * equals方法不处理数据转型的关系
         */
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));

        /**
         * 条件编译，编译之后的class文件中只会有System.out.println("block-1");
         */
        if(true){
            System.out.println("block-1");
        }else{
            System.out.println("block-2");
        }

    }




}
