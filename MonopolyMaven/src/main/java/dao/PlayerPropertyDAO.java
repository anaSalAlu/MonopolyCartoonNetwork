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

	/* Read */
	// public PlayerProperty findPlayerProperty(int playerId, int propertyId, int
	// gameId);

	/* Update */
	public void updatePlayerProperty(PlayerProperty playerProperty);

	/* Delete */
	public void deletePlayerProperty(int playerId, int propertyId, int gameId);

	/* Read All */
	public List<PlayerProperty> findPlayerPropertiesByPlayerAndGame(int playerId, int gameId);

	/* Mirar si una propiedad tiene dueño */
	public boolean isPropertyOwned(int propertyId, int gameId);

	/* Conseguir el dueño de la propiedad */
	public int getPropertyOwner(int propertyId, int gameId);
}
