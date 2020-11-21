package ai.infrrd.training.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required during sign-up")
public class SignUpRequest {

	@ApiModelProperty(notes = "Unique username")
	private String username;
	@ApiModelProperty(notes = "Unique email")
	private String email;
	@ApiModelProperty(notes = "Password which will be remembered!!")
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
