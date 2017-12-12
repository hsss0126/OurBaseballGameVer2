package dto;

public class GameInfo {

	private int id;
	private int roomId;
	private String hostNumber;
	private String awayNumber;
	private String inputNum;
	private int orderUserId;
	private String resultCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getHostNumber() {
		return hostNumber;
	}

	public void setHostNumber(String hostNumber) {
		this.hostNumber = hostNumber;
	}

	public String getAwayNumber() {
		return awayNumber;
	}

	public void setAwayNumber(String awayNumber) {
		this.awayNumber = awayNumber;
	}

	public String getInputNum() {
		return inputNum;
	}

	public void setInputNum(String inputNum) {
		this.inputNum = inputNum;
	}

	public int getOrderUserId() {
		return orderUserId;
	}

	public void setOrderUserId(int orderUserId) {
		this.orderUserId = orderUserId;
	}

	public String getResultCount() {
		return resultCount;
	}

	public void setResultCount(String resultCount) {
		this.resultCount = resultCount;
	}

	@Override
	public String toString() {
		return "GameInfo [id=" + id + ", roomId=" + roomId + "]";
	}

	
}
