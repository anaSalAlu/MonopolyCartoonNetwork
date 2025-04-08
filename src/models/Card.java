package models;

/**
 * @author Ana
 */
public class Card {

	public enum CardType {
		LUCK, COMMUNITY_CHEST
	}

	public int idCard;
	public CardType type;
	public Action action;

	public int getIdCard() {
		return idCard;
	}

	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
