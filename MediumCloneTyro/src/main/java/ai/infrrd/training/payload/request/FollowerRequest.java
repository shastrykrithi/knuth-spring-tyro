package ai.infrrd.training.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Parameters required to follow a user")
public class FollowerRequest {
	
	@ApiModelProperty(notes="The username of the user")
	private String username;
	@ApiModelProperty(notes="The following Request ID")
	private String followRequestID;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFollowRequestID() {
		return followRequestID;
	}
	public void setFollowRequestID(String followRequestID) {
		this.followRequestID = followRequestID;
	}
		

}
