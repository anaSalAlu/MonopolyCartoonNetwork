package dao;

import java.util.List;

import models.PlayerProperty;
import models.Property;

/**
 * @author Ana
 */
public interface PlayerPropertyDAO {

	/* CRUD operations */
	/* Create */
	public void addPlayerProperty(PlayerProperty playerProperty);

	/* Read */
	public Property findPlayerPropertyById(int id);

	/* Update */
	public void updatePlayerProperty(PlayerProperty playerProperty);

	/* Delete */
	public void deletePlayerProperty(int id);

	/* Read All */
	public List<PlayerProperty> getAll();

}
