package models;

/**
 * @author Ana
 */
public class Cell {

	public enum CellType {
		PROPERTY, JAIL, LUCK, COMMUNITY_CHEST
	}

	public int idCell;
	public CellType type;
	public Card card;
	public Property property;

	public Cell(int idCell, CellType type, Card card, Property property) {
		super();
		this.idCell = idCell;
		this.type = type;
		this.card = card;
		this.property = property;
	}

	public int getIdCell() {
		return idCell;
	}

	public void setIdCell(int idCell) {
		this.idCell = idCell;
	}

	public CellType getType() {
		return type;
	}

	public void setType(CellType type) {
		this.type = type;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}
}
