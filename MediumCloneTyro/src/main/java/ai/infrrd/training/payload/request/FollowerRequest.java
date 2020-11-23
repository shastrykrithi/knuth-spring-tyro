package ai.infrrd.training.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required to follow a user")
public class FollowerRequest {

	@ApiModelProperty(notes = "The following Request ID")
	@NotNull(message = "Follower Id can not be null")
	@Size(min = 4, message = "ID allowed length is minimum 4")
	@Pattern(regexp="^[a-zA-Z0-9]+$",message = "No Special characters allowed")
	private String followRequestID;

	public String getFollowRequestID() {
		return followRequestID;
	}

	public void setFollowRequestID(String followRequestID) {
		this.followRequestID = followRequestID;
	}

}
