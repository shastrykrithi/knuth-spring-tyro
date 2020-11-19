package ai.infrrd.training.payload.response;

import java.util.HashSet;

import ai.infrrd.training.dto.UserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Response body with list of followers")
public class FollowerResponse {
	
	@ApiModelProperty(notes="List of topics")
	private HashSet<UserDto> followerlist;

	public FollowerResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FollowerResponse(HashSet<UserDto> followerlist) {
		super();
		this.followerlist = followerlist;
	}

	public HashSet<UserDto> getFollowerlist() {
		return followerlist;
	}

	public void setFollowerlist(HashSet<UserDto> followerlist) {
		this.followerlist = followerlist;
	}
	
	

	
	
	


}
