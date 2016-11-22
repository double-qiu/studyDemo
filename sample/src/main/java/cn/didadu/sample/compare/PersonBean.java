package cn.didadu.sample.compare;

public class PersonBean implements Comparable<PersonBean>{

	 public PersonBean(int age, String name) {  
	        this.age = age;  
	        this.name = name;  
	    }  
	  
	private int age = 0;  
	
	private String name = "";  
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(PersonBean person) {
		// TODO Auto-generated method stub
		  int cop = age - person.getAge();  
		  
		  return cop;
		  
	      /*  if (cop != 0)  
	            return cop;  
	        else  
	            return name.compareTo(person.name);  */
	}

}
