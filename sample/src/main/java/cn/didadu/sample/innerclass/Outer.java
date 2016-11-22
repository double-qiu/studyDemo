package cn.didadu.sample.innerclass;

public class Outer {

	//内部类
	class Inner{
		private int in;
		Inner(int inArgument){
			in = inArgument;
		}
		void print(){
			System.out.println(in);
		}
	}
	
	//通过外部类获取内部类
	public Inner getInner(int inArgument){
		return new Inner(inArgument);
	}
	
	//方法中的内部类
	public FunctionInterface getFunctionClass(int funcArgument){
		class FunctionClass implements FunctionInterface{
			private int funcId;
			private FunctionClass(int argument){
				funcId = argument;
			}
			public void print(){
				System.out.println(funcId);
			}
		}
		return new FunctionClass(funcArgument);
	}
	
	//作用域内的内部类
	public void internalClass(boolean b){
		if(b){
			class InternalClass{
				private int id;
				InternalClass(int idArgument){
					id = idArgument;
				}
				void print(){
					System.out.println(id);
				}
			}
			
			InternalClass internal = new InternalClass(88);
			internal.print();
		}
	}
	
	//匿名内部类
	public InnerClassAbs getFunctionClassByNoName(int id){
		return new InnerClassAbs(id){
			@Override
			public void print() {
				// TODO Auto-generated method stub
				System.out.println(this.id);
			}
			
		};
	}
	
	//静态内部类
	public static class StaticInner{
		private int in;
		StaticInner(int inArgument){
			in = inArgument;
		}
		void print(){
			System.out.println(in);
		}
	} 
	
	public static StaticInner getStaticInner(int inArgument){
		return new StaticInner(inArgument);
	}
}
