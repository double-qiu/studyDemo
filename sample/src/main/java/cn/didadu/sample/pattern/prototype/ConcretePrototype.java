package cn.didadu.sample.pattern.prototype;

public class ConcretePrototype implements Prototype{

	private int id;
	
	public ConcretePrototype(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Prototype clone(){
		
		try {
			return (Prototype) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
			
		}
	}
}
