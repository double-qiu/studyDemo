package cn.didadu.sample.childCloneFather;
public class Child extends Father{
	
	private String age;
	
	public Child(){
		
	}
	
	public Child(Father father) throws Exception{
		super.fatherToChild(father, this);
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	 
}
