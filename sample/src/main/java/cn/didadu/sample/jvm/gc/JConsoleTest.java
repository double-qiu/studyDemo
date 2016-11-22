package cn.didadu.sample.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinggg on 16/3/11.
 */
public class JConsoleTest {

    static class OOMObject{
        public byte[] placeHolder = new byte[64 * 1024];
    }

    public static void fillMap(int num) throws InterruptedException{
        List<OOMObject> list = new ArrayList<>();
        for(int i = 0; i < num; i++){
            //稍作延时，令监视曲线的变化更加明显
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args) throws Exception{
        fillMap(1000);
    }

}
