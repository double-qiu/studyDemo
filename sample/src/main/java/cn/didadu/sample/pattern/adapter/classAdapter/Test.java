package cn.didadu.sample.pattern.adapter.classAdapter;

/**
 * Created by zhangjing on 2015/9/7.
 * Adaptee中只有sampleOperation1
 * Adapter通过Target接口去适配sampleOperation1和sampleOperation2
 */
public class Test {

    public static void main(String[] args) {
        Adapter adapter = new Adapter();
        adapter.sampleOperation1();
        adapter.sampleOperation2();
    }

}
