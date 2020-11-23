package ai.infrrd.training.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required to follow a topic")
public class TopicFollowRequest {

	@ApiModelProperty(notes = "The topic name")
	@NotNull(message = "topics ID can not be null")
	@Pattern(regexp = "[\\S^!.@?*&%#].*$", message = "No Special characters allowed")
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
