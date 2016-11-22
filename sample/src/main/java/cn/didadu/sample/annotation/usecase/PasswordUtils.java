package cn.didadu.sample.annotation.usecase;

import java.util.List;

public class PasswordUtils {

	@UseCase(id = 47, description = "Passwords must contain at least one numeric")
	public boolean validatePassword(String password){
		return (password.matches("\\w*\\d\\w*"));
	}
	
	@UseCase(id = 48)
	public String encryptPassword(String password){
		return new StringBuilder(password).reverse().toString();
	}
	
	@UseCase(id = 49, description = "New passsword cant't equal previously used ones")
	public boolean chechForNewPassword(List<String> prevPasswords,String password){
		return !prevPasswords.contains(password);
	}
}
