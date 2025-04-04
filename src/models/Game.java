package models;

public class Game {

	public enum State {
		IN_GAME, FINISHED, PAUSED
	}

	public int idGame;
	public State state;
	public String duration;

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
