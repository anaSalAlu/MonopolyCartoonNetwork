package dao;

import java.util.List;

import models.Game;

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

}
