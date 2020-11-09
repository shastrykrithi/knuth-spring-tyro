package ai.infrrd.training.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class Users {
	
	@Id
	private String id;
	@Indexed(unique = true)
	private String username;
	private String password;
	@Indexed(unique = true)
	private String email;
	
	
	
	public Users() {
		super();
	}
	
	
	public Users(String username, String password, boolean isActive,String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email
				+ "]";
	}
	
	
	

}