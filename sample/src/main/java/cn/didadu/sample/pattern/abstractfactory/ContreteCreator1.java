package cn.didadu.sample.pattern.abstractfactory;

/**
 * Created by zhangjing on 2015/9/6.
 */
public class ContreteCreator1 implements Creator{


    @Override
    public ProductA factoryA() {
        return new ProductA1();
    }

    @Override
    public ProductB factoryB() {
        return new ProductB1();
    }
}
