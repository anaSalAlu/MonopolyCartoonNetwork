package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Game;
import models.Game.State;
import models.Player;

/**
 * @author Ana
 */
public class GameDAOSQLITE implements GameDAO {

	@Override
	public int addGame(Game game) {
		String sql = "INSERT INTO Game(state, duration) VALUES (?, ?)";
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			conn = ManagerConnection.obtenirConnexio();
			statement = conn.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);
			statement.setString(1, game.getState().name());
			statement.setString(2, game.getDuration());
			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				return id;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public Game findGameById(int id) {
		String sql = "SELECT * FROM Game WHERE id_game = ?";
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			conn = ManagerConnection.obtenirConnexio();
			statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int gameId = resultSet.getInt("id_game");
				String typeString = resultSet.getString("state");
				String duration = resultSet.getString("duration");
				return new Game(gameId, State.valueOf(typeString), duration);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void updateGame(Game game) {
		String sql = "UPDATE Game SET state = ?, duration = ? WHERE id_game = ?";
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = ManagerConnection.obtenirConnexio();
			statement = conn.prepareStatement(sql);
			statement.setString(1, game.getState().name());
			statement.setString(2, game.getDuration());
			statement.setInt(3, game.getIdGame());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteGame(int id) {
		String sql = "DELETE FROM Game WHERE id_game = ?";
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = ManagerConnection.obtenirConnexio();
			statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Game> getAll() {
		List<Game> games = new ArrayList<Game>();
		String sql = "SELECT * FROM Card";
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			conn = ManagerConnection.obtenirConnexio();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int gameId = resultSet.getInt("id_game");
				String typeString = resultSet.getString("type");
				String duration = resultSet.getString("duration");

				Game game = new Game(gameId, State.valueOf(typeString), duration);
				games.add(game);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

}