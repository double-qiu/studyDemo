package cn.didadu.sample.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 输出内容至外部的一个文件
 * @author zhangjing
 *
 */
public class OutputStreamDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
         OutputStreamDemo od = new OutputStreamDemo();  
           // od.writeWithByte();  
            od.writeWithByteArray();  
    }

    public void writeWithByte(){
        String fileName = "F:" + File.separator + "test.txt";  
        OutputStream out = null;  
        File f = new File(fileName);  
        
        try {  
            out = new FileOutputStream(f, true);  
            String str = "   [Publicity ministry of ShangHai Municipal committee of CPC]";  
            byte[] b = str.getBytes();  
            out.write(b);  
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
    
    public void writeWithByteArray(){
        String fileName = "F:" + File.separator + "test.txt";  
        OutputStream out = null;  
        File f = new File(fileName);  
        
        try {  
            out = new FileOutputStream(f, true);  
            String str = "   [hello with byte yi ge ge xie]";  
            byte[] b = str.getBytes();  
            for (int i = 0; i < b.length; i++) {  
                out.write(b[i]);  
            }  
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
}
