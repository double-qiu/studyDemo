package cn.didadu.sample.pattern.builder;

/**
 * 建造者模式
 */
public class Test {

	public static void main(String[] args){
		Director director = new Director();
		director.construct();
	}
}
