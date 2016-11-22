package cn.didadu.sample.pattern.adapter.cube2ball;

/**
 * Created by zhangjing on 2015/9/7.
 * MagicFinger通过实现BallIF接口和Cube类型的成员变量来实现将正方体的宽的内切圆
 */
public class Test {
    public static void main(String[] args) {
        Cube cube = new Cube(4F);
        MagicFinger magic = new MagicFinger(cube);
        System.out.println(magic.calculateArea());
        System.out.println(magic.calculateVolume());
    }
}
