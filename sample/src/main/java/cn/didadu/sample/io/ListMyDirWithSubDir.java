package cn.didadu.sample.io;

import java.io.File;

/**
 * 列出给出路径下所有的目录，包括子目录
 * @author zhangjing
 *
 */
public class ListMyDirWithSubDir {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String fileName = "F:" + File.separator + "工作相关";  
	     File f = new File(fileName);  
	     ListMyDirWithSubDir listDir = new ListMyDirWithSubDir();  
	     listDir.print(f);  
	}
	
	public void  print(File f){
		if(f != null){
			if(f.isDirectory()){
				 File[] fileArray = f.listFiles();  
	                if (fileArray != null) {  
	                    for (int i = 0; i < fileArray.length; i++) {  
	                        print(fileArray[i]);  
	                    }  
	                }  
			}else{
				System.out.println(f);  
			}
		}
	}

}
