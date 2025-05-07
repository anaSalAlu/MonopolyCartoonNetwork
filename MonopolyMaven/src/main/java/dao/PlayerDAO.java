package dao;

import java.util.List;

import models.Player;

/**
 * @author Ana
 */
public interface PlayerDAO {

	/* CRUD operations */
	/* Create */
	public void addPlayer(Player player);

	/* Read */
	public Player findPlayerById(int id);

	/* Update */
	public void updatePlayer(Player player);

	/* Delete */
	public void deletePlayer(int id);

	/* Read All */
	public List<Player> getAll();

	public List<Player> cargarJugadores();

	public void updatePlayerFicha(Player jugador1);

}
