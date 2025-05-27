package models;

import java.util.List;

import javafx.scene.image.ImageView;

/**
 * @author Ana
 */
public class Player {

	public int idPlayer;
	public Profile profile;
	public Cell cell;
	public int money;
	public List<Card> cards;
	public List<Property> properties;
	public Game game;
	public boolean isBankrupt;
	public int jailTurnsLeft;
	public String token;
	public ImageView imgToken;

	public Player(int idPlayer, Profile profile, Cell cell, int money, List<Card> cards, List<Property> properties,
			Game game, boolean isBankrupt, int jailTurnsLeft) {
		super();
		this.idPlayer = idPlayer;
		this.profile = profile;
		this.cell = cell;
		this.money = money;
		this.cards = cards;
		this.properties = properties;
		this.game = game;
		this.isBankrupt = isBankrupt;
		this.jailTurnsLeft = jailTurnsLeft;
	}

	public Player() {
	}

	public int getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean getIsBankrupt() {
		return isBankrupt;
	}

	public void setBankrupt(boolean isBankrupt) {
		this.isBankrupt = isBankrupt;
	}

	public int getJailTurnsLeft() {
		return jailTurnsLeft;
	}

	public void setJailTurnsLeft(int jailTurnsLeft) {
		this.jailTurnsLeft = jailTurnsLeft;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ImageView getImgToken() {
		return imgToken;
	}

	public void setImgToken(ImageView imgToken) {
		this.imgToken = imgToken;
	}

}