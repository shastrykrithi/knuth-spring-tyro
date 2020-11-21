package ai.infrrd.training.payload.response;

import java.util.HashSet;

import ai.infrrd.training.dto.TopicsDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Response body with list of topics available")
public class TopicsResponse {

	@ApiModelProperty(notes = "List of topics")
	private HashSet<TopicsDto> topicslist;

	public TopicsResponse() {
	}

	public TopicsResponse(HashSet<TopicsDto> topicslist) {
		super();
		this.topicslist = topicslist;
	}

	public HashSet<TopicsDto> getTopicslist() {
		return topicslist;
	}

	public void setTopicslist(HashSet<TopicsDto> topicslist) {
		this.topicslist = topicslist;
	}

}
