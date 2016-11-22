package cn.didadu.sample.jvm.overflow;

/**
 * -Xss160k StackOverflowError
 * Created by jinggg on 16/3/9.
 */
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak(){
        stackLength ++;
        stackLeak();
    }

    public static void main(String[] args){
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try{
            oom.stackLeak();
        }catch (Throwable e){
            System.out.println("scack length: " + oom.stackLength);
            throw e;
        }
    }

}
