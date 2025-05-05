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

	/* Read */
	public PlayerCard findPlayerCardById(int id);

	/* Update */
	public void updatePlayerCard(PlayerCard playerCard);

	/* Delete */
	public void deletePlayerCard(int id);

	/* Read All */
	public List<PlayerCard> getAll();

}
