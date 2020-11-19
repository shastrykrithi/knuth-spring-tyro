package ai.infrrd.training.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Parameters required to follow a topic")
public class TopicFollowRequest {
	
	@ApiModelProperty(notes="The username of the user")
	private String username;
	@ApiModelProperty(notes="The topic name")
	private String topicID;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTopicID() {
		return topicID;
	}
	public void setTopicID(String topicID) {
		this.topicID = topicID;
	}
	

	
	
	
	

}
