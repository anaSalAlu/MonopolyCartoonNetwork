package dao;

import java.util.List;

import models.PlayerProperty;

/**
 * @author Ana
 */
public interface PlayerPropertyDAO {

	/* CRUD operations */
	/* Create */
	public void addPlayerProperty(PlayerProperty playerProperty);

	/* Update */
	public void updatePlayerProperty(PlayerProperty playerProperty);

	/* Delete */
	public void deletePlayerProperty(int playerId, int propertyId, int gameId);

	/* Read All */
	public List<PlayerProperty> findPlayerPropertiesByPlayerAndGame(int playerId, int gameId);

}
