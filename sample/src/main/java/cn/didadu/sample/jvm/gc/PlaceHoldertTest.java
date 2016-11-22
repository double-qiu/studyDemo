package cn.didadu.sample.jvm.gc;

/**
 * Created by jinggg on 16/3/14.
 */
public class PlaceHoldertTest {

    public static void main(String[] args){
        {
            byte[] placeHolder = new byte[64 * 1024 * 1024];
        }
        //加入以下代码，内存被回收
        int a = 0;
        System.gc();
    }

}
