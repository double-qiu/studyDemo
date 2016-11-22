package cn.didadu.sample.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 复制文件
 * 用BufferInputStream比较快
 * BufferInputStream是一次性读到缓冲区，减少了读取文件的次数
 * @author zhangjing
 *
 */
public class CopyFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 CopyFile cp = new CopyFile();  
	        String src = "F:" + File.separator + "apue3.rar";  
	        String des = "F:" + File.separator + "apue3(copy).rar";  
	        long sTime = System.currentTimeMillis();  
	        //cp.copy(src, des);
	        cp.copyByBuffer(src, des);  
	        long eTime = System.currentTimeMillis();  
	        System.out.println("Total spend: " + (eTime - sTime));  
	}

	public void copy(String src, String des){
		  File srcFile = new File(src);  
	      File desFile = new File(des);  
	      InputStream in = null;  
	      OutputStream out = null;  
	      
	      try{
	    	  in = new FileInputStream(srcFile);  
	          out = new FileOutputStream(desFile);  
	    	  
	          byte[] b = new byte[(int) srcFile.length()];  
	          
	          for (int i = 0; i < b.length; i++) {  
	                b[i] = (byte) in.read();  
	          }  
	          
	          out.write(b);  
	          System.out.println("copied [" + srcFile.getName() + "]    with    "  
	                    + srcFile.length()); 
	          
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
	            try {  
	                if (in != null) {  
	                    in.close();  
	                    in = null;  
	                }  
	            } catch (Exception e) {  
	            }  
	        }  
	}
	
	 public void copyByBuffer(String src, String des) {  
	        File srcFile = new File(src);  
	        File desFile = new File(des);  
	        BufferedInputStream bin = null;  
	        BufferedOutputStream bout = null;  
	        try {  
	            bin = new BufferedInputStream(new FileInputStream(srcFile));  
	            bout = new BufferedOutputStream(new FileOutputStream(desFile));  
	            byte[] b = new byte[1024];  
	            while (bin.read(b) != -1) {  
	                bout.write(b);  
	            }  
	            bout.flush();  
	            System.out.println("copied [" + srcFile.getName() + "]    with    "  
	                    + srcFile.length());  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if (bout != null) {  
	                    bout.close();  
	                    bout = null;  
	                }  
	            } catch (Exception e) {  
	            }  
	            try {  
	                if (bin != null) {  
	                    bin.close();  
	                    bin = null;  
	                }  
	            } catch (Exception e) {  
	            }  
	        }  
	    }  
}
