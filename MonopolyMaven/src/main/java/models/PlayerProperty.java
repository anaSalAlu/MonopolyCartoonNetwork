package models;

public class PlayerProperty {

	private int playerId;
	private int propertyId;
	private int gameId;

	public PlayerProperty(int playerId, int propertyId, int gameId) {
		super();
		this.playerId = playerId;
		this.propertyId = propertyId;
		this.gameId = gameId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
}
