package dao;

import java.util.List;

import models.PlayerCard;

/**
 * @author Ana
 */
public interface PlayerCardDAO {

	/* CRUD operations */
	/* Create */
	public void addPlayerCard(PlayerCard playerCard);

	/* Update */
	public void updatePlayerCard(PlayerCard playerCard);

	/* Delete */
	public void deletePlayerCard(int playerId, int gameId, int cardId);

	/* Read All */
	public List<PlayerCard> findPlayerCardsByPlayerAndGame(int playerId, int gameId);

}
