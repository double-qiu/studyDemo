package cn.didadu.sample.jvm.overflow;

import java.util.ArrayList;
import java.util.List;

/**
 * -verbose:gc -Xms20m -Xmx20m  Heap OutOfMemoryError
 * Created by jinggg on 16/3/9.
 */
public class HeapOOM {

    static class OOMObject{

    }

    public static void main(String[] args){
        List<OOMObject> list = new ArrayList<>();

        while(true){
            list.add(new OOMObject());
        }

    }

}
