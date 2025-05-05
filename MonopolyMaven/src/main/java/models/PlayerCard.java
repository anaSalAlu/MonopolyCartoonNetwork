package models;

public class PlayerCard {

	private int playerId;
	private int cardId;
	private int gameId;

	public PlayerCard(int playerId, int cardId, int gameId) {
		super();
		this.playerId = playerId;
		this.cardId = cardId;
		this.gameId = gameId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

}
