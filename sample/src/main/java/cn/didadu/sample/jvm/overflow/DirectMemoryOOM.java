package cn.didadu.sample.jvm.overflow;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/** -Xmx5M -XX:MaxDirectMemorySize=1M
 * Created by jinggg on 16/3/10.
 */
public class DirectMemoryOOM {

    public static void main(String[] args) throws Exception{
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe)unsafeField.get(null);
        while (true){
            unsafe.allocateMemory(1024 * 1024 * 1024);
        }
    }

}
