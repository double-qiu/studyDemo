package cn.didadu.sample.pattern.abstractfactory;

/**
 * Created by zhangjing on 2015/9/6.
 * 抽象工厂模式
 * 用来生产不同产品族的全部产品。（对于增加新的产品，无能为力；支持增加产品族）
 */
public class Client {

    private static Creator creator1,creator2;
    private static ProductA productA1,productA2;
    private static ProductB productB1,productB2;
    public static void main(String[] args){
        creator1 = new ContreteCreator1();
        productA1 = creator1.factoryA();
        productB1 = creator1.factoryB();

        creator2 = new ContreteCreator2();
        productA2 = creator2.factoryA();
        productB2 = creator2.factoryB();
    }

}
