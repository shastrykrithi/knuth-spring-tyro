package ai.infrrd.training.dto;

public class NotificationsDto {

	private String id;
	private Boolean isread;

	public NotificationsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotificationsDto(String id, Boolean isread) {
		super();
		this.id = id;
		this.isread = isread;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsread() {
		return isread;
	}

	public void setIsread(Boolean isread) {
		this.isread = isread;
	}

}
