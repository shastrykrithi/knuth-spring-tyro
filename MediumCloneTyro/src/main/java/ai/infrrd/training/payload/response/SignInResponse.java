package ai.infrrd.training.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Response body for successful sign-in")
public class SignInResponse {

	@ApiModelProperty(notes = "User ID")
	private String id;
	@ApiModelProperty(notes = "User username")
	private String username;
	@ApiModelProperty(notes = "User email")
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
