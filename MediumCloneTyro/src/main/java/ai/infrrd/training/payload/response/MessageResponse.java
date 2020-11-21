package ai.infrrd.training.payload.response;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Response message for the request")
public class MessageResponse {

	private String message;

	public MessageResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
