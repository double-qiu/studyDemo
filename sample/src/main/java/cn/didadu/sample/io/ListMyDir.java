package cn.didadu.sample.io;

import java.io.File;

/**
 * 列出给出路径下所有的目录，不包括子目录
 * @author zhangjing
 *
 */
public class ListMyDir {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = "F:" + File.separator + "工作相关";
		File f = new File(fileName);
		File[] fs = f.listFiles();
		for(int i = 0; i < fs.length;i++){
			System.out.println(fs[i].getName());
		}
	}

}
