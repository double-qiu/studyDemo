package cn.didadu.sample.jvm.overflow;

/**
 * Created by jinggg on 16/3/9.
 */
public class JavaVMStackOOM {

    private void dontStop(){
        while(true){

        }
    }

    public void stackLeakByThread(){

        while(true){
            new Thread(() -> {
                dontStop();
            }).start();
        }
    }

    public static void main(String[] args){
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }


}
