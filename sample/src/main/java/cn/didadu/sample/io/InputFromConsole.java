package cn.didadu.sample.io;

import java.io.IOException;

/**
 * 从console接受用户输入的字符，如和用户有交互的命令行。
 * @author zhangjing
 *
 */
public class InputFromConsole {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int a = 0;
		byte[] input = new byte[1024];
		System.in.read(input);
		
		 System.out.println("your input is: " + new String(input));  
	}

}
