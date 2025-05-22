package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Board;
import models.Game;
import models.Game.State;
import models.Player;
import models.Property;

/**
 * @author Ana
 */
public class GameDAOSQLITE implements GameDAO {

	@Override
	public void addGame(Game game) {
		String sql = "INSERT INTO Game(state, duration) VALUES (?, ?)";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, game.getState().name());
			statement.setString(2, game.getDuration());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Game findGameById(int id) {
		String sql = "SELECT * FROM Game WHERE id_game = ?";

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int gameId = resultSet.getInt("id_game");
				String typeString = resultSet.getString("state");
				String duration = resultSet.getString("duration");
				return new Game(gameId, State.valueOf(typeString), duration);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateGame(Game game) {
		String sql = "UPDATE Game SET state = ?, duration = ? WHERE id_game = ?";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, game.getState().name());
			statement.setString(2, game.getDuration());
			statement.setInt(3, game.getIdGame());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteGame(int id) {
		String sql = "DELETE FROM Game WHERE id_game = ?";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Game> getAll() {
		List<Game> games = new ArrayList<Game>();
		String sql = "SELECT * FROM Card";

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int gameId = resultSet.getInt("id_game");
				String typeString = resultSet.getString("type");
				String duration = resultSet.getString("duration");

				Game game = new Game(gameId, State.valueOf(typeString), duration);
				games.add(game);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return games;
	}

	@Override
	public List<Player> loadPlayers() {
		// Implementación de ejemplo: reemplaza con la lógica real de la base de datos
		List<Player> players = new ArrayList<>();
		// Agrega lógica para obtener jugadores desde la base de datos
		System.out.println("Cargando jugadores desde la base de datos...");
		return players;
	}

	@Override
	public List<Property> loadProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Board loadBoards() {
		// TODO Auto-generated method stub
		return null;
	}

}