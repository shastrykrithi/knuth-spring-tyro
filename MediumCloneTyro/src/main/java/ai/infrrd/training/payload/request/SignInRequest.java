package ai.infrrd.training.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Parameters required during sign-in")
public class SignInRequest {
	
	@ApiModelProperty(notes="The email provided during sign-up")
	private String email;
	@ApiModelProperty(notes="The password provided during sign-up")
	private String password;

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
