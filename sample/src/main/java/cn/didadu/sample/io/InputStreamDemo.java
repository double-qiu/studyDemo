package cn.didadu.sample.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 从外部读入一个文件
 * @author zhangjing
 *
 */
public class InputStreamDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 InputStreamDemo id = new InputStreamDemo();  
	     String src = "F:" + File.separator + "工作相关" + File.separator + "CF.txt";  
	     id.readFile(src);  
	}

	public void readFile(String fileName){
		File srcFile = new File(fileName);
		InputStream in = null;
		
		try{
			in = new FileInputStream(srcFile);
			byte[] b = new byte[(int)srcFile.length()];
			for (int i = 0; i < b.length; i++) {  
                b[i] = (byte) in.read();  
            }  
			System.out.println(new String(b));
		}catch(Exception e){
			e.printStackTrace();  
		}finally{
			try {  
                if (in != null) {  
                    in.close();  
                    in = null;  
                }  
            } catch (Exception e) {  
            }  
		}
	}
}
