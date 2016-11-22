package cn.didadu.sample.jvm.overflow;

/** -XX:PermSize=10M -XX:PermSize=10M
 * Created by jinggg on 16/3/10.
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args){

        /**
         * JDK1.7+不会OOM，因为常量池不在方法去里了
         */
        /*List<String> list = new ArrayList<>();
        int i = 0;
        while (true){
            list.add(String.valueOf(i++).intern());
        }*/

        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str3 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str3.intern() == str3);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

    }

}
