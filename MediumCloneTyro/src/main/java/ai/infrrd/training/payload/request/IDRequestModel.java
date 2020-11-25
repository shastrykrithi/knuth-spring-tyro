package ai.infrrd.training.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required to follow a user")
public class IDRequestModel {

	@ApiModelProperty(notes = "The Request ID")
	@NotNull(message = "Id can not be null")
	@Size(min = 4, message = "ID allowed length is minimum 4")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "No Special characters allowed")
	private String id;

	public IDRequestModel(String id) {
		super();
		this.id = id;
	}

	public IDRequestModel() {
		super();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
