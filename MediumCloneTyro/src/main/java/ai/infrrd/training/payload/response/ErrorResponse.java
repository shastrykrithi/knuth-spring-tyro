package ai.infrrd.training.payload.response;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Error message for the request")
public class ErrorResponse {

	private MessageResponse error;

	public ErrorResponse(MessageResponse error) {
		super();
		this.error = error;
	}

	public MessageResponse getError() {
		return error;
	}

	public void setError(MessageResponse error) {
		this.error = error;
	}

}
