package ai.infrrd.training.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required during sign-up")
public class SignUpRequest {

	@ApiModelProperty(notes = "Unique username")
	@NotNull(message = "user name can not be empty")
	@Size(min = 5, max = 50, message = "Allowed length of username is minimum 5 and maximum 50")
	@Pattern(regexp = "[\\S^0-9!.@?*&%#].*$", message = "Only Alphabets are allowed")
	private String username;
	@ApiModelProperty(notes = "Unique email")
	@NotNull(message = "email can not be null")
	@Size(min = 5, message = "email cannot be empty")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Incorrect email pattern")
	private String email;
	@ApiModelProperty(notes = "Password which will be remembered!!")
	@NotNull(message = "password can not be empty")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[@#?!$%^&*-]).{8,}$", message = "Password atleast one uppercase, one lowercase, one number and one special character")
	@Size(min = 8, message = "Allowed length of password is minimum 8")
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
