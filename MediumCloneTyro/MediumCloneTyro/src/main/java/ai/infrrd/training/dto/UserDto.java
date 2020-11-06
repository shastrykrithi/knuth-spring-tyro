package ai.infrrd.training.dto;

public class UserDto {
	
	
	private String username;
	private String password;
	private String email;
	
	
	
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public UserDto(String username, String password, boolean isActive,String email) {
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