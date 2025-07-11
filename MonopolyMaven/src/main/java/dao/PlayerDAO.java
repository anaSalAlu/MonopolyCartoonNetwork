package dao;

import java.util.List;

import models.Player;

/**
 * @author Ana
 */
public interface PlayerDAO {

	/* CRUD operations */
	/* Create */
	public int addPlayer(Player player);

	/* Read */
	public Player findPlayerById(int id);

	/* Update */
	public void updatePlayer(Player player);

	/* Delete */
	public void deletePlayer(int id);

	/* Read All */
	public List<Player> getAll();

}
