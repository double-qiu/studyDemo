package cn.didadu.sample.immutableClass;

/**
 * Created by zhangjing on 2015/9/7.
 * 构建不可变类
 * 1. 所有成员都是private
 * 2. 不提供对成员的改变方法，例如：setXXXX
 * 3. 确保所有的方法不会被重载。手段有两种：使用final Class(强不可变类)，或者将所有类方法加上final(弱不可变类)。
 * 4. 如果某一个类成员不是原始变量(primitive)或者不可变类，必须通过在成员初始化(in)或者get方法(out)时通过深度clone方法，来确保类的不可变
 */
public class MyImmutable {

    private final int[] myArray;

    public MyImmutable(int[] anArray) {
        //引用都指向原来的地址，若之前地址的值改变了，这里的值也会变
        //this.myArray = anArray; // wrong

        //数组的克隆是深度克隆，相当于重新生成了一个对象，原来的值改变时，对此处的值没有影响
        this.myArray = anArray.clone();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("Numbers are: ");
        for (int i = 0; i < myArray.length; i++) {
            sb.append(myArray[i] + " ");
        }
        return sb.toString();
    }

    public static void main(String[] args){
        int[] a = {1,2,3};
        MyImmutable mm = new MyImmutable(a);
        a[1] = 9;
        System.out.println(mm.toString());
    }
}
