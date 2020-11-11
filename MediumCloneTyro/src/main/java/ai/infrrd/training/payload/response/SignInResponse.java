package ai.infrrd.training.payload.response;

public class SignInResponse {
	
	private String id;
	private String username;
	private String email;
	
	
	public SignInResponse(String id, String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
