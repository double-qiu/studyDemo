package cn.didadu.sample.innerclass;



public class Test {
	
	public static void main(String[] args){
		
		Outer out = new Outer();
		
		//通过外部类提供的方法创建内部类对象
		Outer.Inner in1 = out.getInner(10);
		in1.print();
		
		//通过new创建内部类对象
		Outer.Inner in2 = out.new Inner(2);
		in2.print();
		
		//方法内的内部类
		FunctionInterface funcClass = out.getFunctionClass(3);
		funcClass.print();
		
		//作用域内的内部类
		out.internalClass(true);
		
		//匿名内部类
		out.getFunctionClassByNoName(18).print();
		
		//静态内部类(不需要依赖实例化外部类)
		Outer.StaticInner staticInner = Outer.getStaticInner(99);
		staticInner.print();
	}
}
