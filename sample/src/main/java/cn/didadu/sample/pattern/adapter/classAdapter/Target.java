package cn.didadu.sample.pattern.adapter.classAdapter;

public interface Target {

	/**
	 * 这是源类也有的方法sampleOperation1
	 */
	void sampleOperation1();
	
	/**
	 * 这是源类没有的方法sampleOperation1
	 */
	void sampleOperation2();
}
