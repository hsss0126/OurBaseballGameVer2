package dto;

import java.math.BigDecimal;

public class User {
	
	int id;
	String nickName;
	int stateId;
	String stateName;
	int win;
	int lose;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public float getRate() {
		if(this.win==0 && this.lose==0) return 0;
		BigDecimal numerator, denominator, result, per;
		per = new BigDecimal(100);
		numerator = new BigDecimal(this.getWin());
		denominator = new BigDecimal(this.getWin()+this.getLose());
		result = numerator.divide(denominator, 3, BigDecimal.ROUND_DOWN);
		result = result.multiply(per);
		return result.floatValue();
	}

	public String getRecord() {
		return String.format("%d승 / %d패 (%.1f%%)", this.getWin(), this.getLose(), this.getRate());
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nickName=" + nickName + ", stateName=" + stateName + ", win=" + win + ", lose="
				+ lose + ", getRate()=" + getRate() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + lose;
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result + ((stateName == null) ? 0 : stateName.hashCode());
		result = prime * result + win;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (lose != other.lose)
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		if (win != other.win)
			return false;
		return true;
	}

	

	
}
