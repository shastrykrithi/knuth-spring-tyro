package ai.infrrd.training.payload.response;

import io.swagger.annotations.ApiModel;

@ApiModel(description="Success message for the request")
public class SuccessResponse {
	
	private MessageResponse result;
	
	

public SuccessResponse(MessageResponse result) {
		super();
		this.result = result;
	}

	public MessageResponse getResult() {
		return result;
	}

	public void setResult(MessageResponse result) {
		this.result = result;
	}
	
	

}
