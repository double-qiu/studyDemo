package cn.didadu.sample.pattern.adapter.objectAdapter;

/**
 * 与classAdapter不同的是不继承Adaptee
 * 而是将Adaptee变成成员变量，构造Adapter的时候必须传入Adaptee
 */
public class Adapter implements Target{

	private Adaptee adaptee;
	
	public Adapter(Adaptee adaptee){
		super();
		this.adaptee = adaptee;
	}
	
	/**
	 * 源类中有方法sampleOperation1
	 * 因此适配器直接委派即可
	 */
	@Override
	public void sampleOperation1() {
		// TODO Auto-generated method stub
		adaptee.sampleOperation1();
	}

	/**
	 * 源类中没有方法sampleOperation2
	 * 因此适配器类需要补充此方法
	 */
	@Override
	public void sampleOperation2() {
		//write your code here
	}

}
