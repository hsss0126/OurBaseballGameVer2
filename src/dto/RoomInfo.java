package dto;

public class RoomInfo {

	int id;
	String roomName;
	int hostId;
	String hostName;
	int awayId;
	String awayName;
	int level;
	int userCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getAwayId() {
		return awayId;
	}

	public void setAwayId(int awayId) {
		this.awayId = awayId;
	}

	public String getAwayName() {
		return awayName;
	}

	public void setAwayName(String awayName) {
		this.awayName = awayName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	@Override
	public String toString() {
		return "RoomInfo [id=" + id + ", roomName=" + roomName + ", hostId=" + hostId + ", hostName=" + hostName
				+ ", awayId=" + awayId + ", awayName=" + awayName + ", level=" + level + ", userCount=" + userCount
				+ "]";
	}

	
}
