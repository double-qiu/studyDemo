package cn.didadu.sample.exception;

public class Test {

	public static void main(String[] args) {
		Test test = new Test();
		//test.check();//CheckedException需要捕获
		test.unCheck();//运行时异常属于UnCheckedException，不需要捕获
	}
	
	public void check() throws CheckedException{
		throw new CheckedException();
	}
	
	public void unCheck() {
		throw new UnCheckedException();
	}
}
