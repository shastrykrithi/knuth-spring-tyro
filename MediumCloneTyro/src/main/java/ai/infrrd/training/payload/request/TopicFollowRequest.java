package ai.infrrd.training.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required to follow a topic")
public class TopicFollowRequest {

	@ApiModelProperty(notes = "The topic name")
	private String topicID;

	public TopicFollowRequest() {

	}

	public TopicFollowRequest(String topicID) {
		super();
		this.topicID = topicID;
	}

	public String getTopicID() {
		return topicID;
	}

	public void setTopicID(String topicID) {
		this.topicID = topicID;
	}

}
