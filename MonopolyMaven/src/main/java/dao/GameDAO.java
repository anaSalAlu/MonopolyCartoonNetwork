package dao;

import java.util.List;

import models.Board;
import models.Game;
import models.Player;
import models.Property;

/**
 * @author Ana
 */
public interface GameDAO {

	/* CRUD operations */
	/* Create */
	public void addGame(Game game);

	/* Read */
	public Game findGameById(int id);

	/* Update */
	public void updateGame(Game game);

	/* Delete */
	public void deleteGame(int id);

	/* Read All */
	public List<Game> getAll();

	public List<Player> loadPlayers();

	public List<Property> loadProperties();

	public Board loadBoards();

}
