package dto;

public class BallCount {

	private int ball;
	private int strike;
	private boolean out;

	public int getBall() {
		return ball;
	}

	public void setBall(int ball) {
		this.ball = ball;
	}

	public int getStrike() {
		return strike;
	}

	public void setStrike(int strike) {
		this.strike = strike;
	}

	public boolean isOut() {
		return out;
	}

	public void setOut(boolean out) {
		this.out = out;
	}
	
	@Override
	public String toString() {
		if(this.out) return "OUT";
		return String.format("%dS %dB", this.strike, this.ball);
	}
}
