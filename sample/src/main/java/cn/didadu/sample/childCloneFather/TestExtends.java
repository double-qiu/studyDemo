package cn.didadu.sample.childCloneFather;

public class TestExtends {

	static public  void main(String[] args){
		Father father = new Father();
		father.setId(1);
		father.setName("I am father");

		try {
			Child child = new Child(father);
			child.setAge("23");
			
			System.out.println(child.getId());
			System.out.println(child.getName());
			System.out.println(child.getAge());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
