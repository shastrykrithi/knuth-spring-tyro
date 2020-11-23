package ai.infrrd.training.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required during sign-in")
public class SignInRequest {

	@ApiModelProperty(notes = "The email provided during sign-up")
	@NotNull(message = "email can not be null")
	@Size(min = 5, message = "email cannot be empty")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Incorrect email pattern example@example.example")
	private String email;
	@ApiModelProperty(notes = "The password provided during sign-up")
	@NotNull(message = "password can not be empty")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[@#?!$%^&*-]).{8,}$", message = "Password atleast one uppercase, one lowercase, one number and one special character")
	@Size(min = 8, message = "Allowed length of password is minimum 8")
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
