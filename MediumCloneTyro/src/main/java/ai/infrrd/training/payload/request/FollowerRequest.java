package ai.infrrd.training.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required to follow a user")
public class FollowerRequest {

	@ApiModelProperty(notes = "The following Request ID")
	@NotNull(message = "Follower Id can not be null")
	@Pattern(regexp = "[\\S^!.@?*&%#].*$", message = "No Special characters allowed")
	private String followRequestID;

	public String getFollowRequestID() {
		return followRequestID;
	}

	public void setFollowRequestID(String followRequestID) {
		this.followRequestID = followRequestID;
	}

}
