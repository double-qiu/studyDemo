package cn.didadu.sample.io;

import java.io.PipedInputStream;

public class ReceiveMessage implements Runnable{

    private PipedInputStream input = null;  
      
    public PipedInputStream getInput() {  
        return this.input;  
    }  
  
    public ReceiveMessage() {  
        this.input = new PipedInputStream();  
    }  
    
    private void receive() {  
          
        byte[] b = new byte[1024];  
        int len = 0;  
        String msg = "";  
        try {  
            len = input.read(b);  
            msg = new String(b, 0, len);  
            if (msg.equals("start")) {  
                System.out  
                        .println("received the start message, receive now can do something......");  
                Thread.interrupted();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (input != null) {  
                    input.close();  
                    input = null;  
                }  
            } catch (Exception e) {  
            }  
        }  
    }  

    @Override
    public void run() {
        // TODO Auto-generated method stub
         try {  
                receive();  
            } catch (Exception e) {  
            }  
    }

}
