package cn.didadu.sample.annotation.database;

@DBTable(name = "MEMBER")
public class Member {
	
	@SQLString(30)
	private String firstName;
	
	@SQLString(50)
	private String lastName;
	
	@SQLInteger
	private Integer age;
	
	@SQLString(value = 30, constraitns = @Constraints(primaryKey = true))
	private String handle;
	
	static int memberCount;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getAge() {
		return age;
	}

	public String getHandle() {
		return handle;
	}
	
	public String toString(){
		return handle;
	}
	

}
