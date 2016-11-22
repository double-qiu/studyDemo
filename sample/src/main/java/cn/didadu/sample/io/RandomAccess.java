package cn.didadu.sample.io;

import java.io.File;
import java.io.RandomAccessFile;

public class RandomAccess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  RandomAccess randomA = new RandomAccess();  
	        randomA.writeToFile();  
	}
	
	 public void writeToFile() {  
	        String fileName = "F:" + File.separator + "test.txt";  
	        RandomAccessFile randomIO = null;  
	        try {  
	  
	            File f = new File(fileName);  
	            randomIO = new RandomAccessFile(f, "rw");  
	            randomIO.writeBytes("asdsad");  
	            randomIO.writeInt(12);  
	            randomIO.writeBoolean(true);  
	            randomIO.writeChar('A');  
	            randomIO.writeFloat(1.21f);  
	            randomIO.writeDouble(12.123);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if (randomIO != null) {  
	                    randomIO.close();  
	                    randomIO = null;  
	                }  
	            } catch (Exception e) {  
	            }  
	        }  
	    }  

}
