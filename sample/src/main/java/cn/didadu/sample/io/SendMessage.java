package cn.didadu.sample.io;

import java.io.PipedOutputStream;
/**
 * 这个流很特殊，我们在线程操作时，两个线程都在运行，这时通过发送一个指令让某个线程do something，
 * 我们在以前的jdk1.4中为了实现这样的功能，使用的就是这个PipeStream
 * 发送一个指令
 * @author zhangjing
 *
 */
public class SendMessage implements Runnable {

    private PipedOutputStream out = null;

    public PipedOutputStream getOut() {
        return this.out;
    }

    public SendMessage() {
        this.out = new PipedOutputStream();
    }
    
    public void send() {  
          
        String msg = "start";  
        try {  
            out.write(msg.getBytes());  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                    out = null;  
                }  
            } catch (Exception e) {  
            }  
        }  
    }  

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {  
            System.out.println("waiting for signal...");  
            Thread.sleep(2000);  
            send();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

}
