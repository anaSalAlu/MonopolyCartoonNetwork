package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Card;
import models.Cell;
import models.Game;
import models.Player;
import models.Profile;
import models.Property;

/**
 * @author Ana
 */
public class PlayerDAOSQLITE implements PlayerDAO {

	@Override
	public void addPlayer(Player player) {
		String sql = "INSERT INTO Player(id_player, profile_id, cell_id, money, cards, properties, game_id, is_bankrupt, jail_turns_left) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, player.getIdPlayer());
			statement.setInt(2, player.getProfile().getIdProfile());
			statement.setInt(3, player.getCell().getIdCell());
			statement.setInt(4, player.getMoney());
			statement.setString(5, player.getCards().toString());
			statement.setString(6, player.getProperties().toString());
			statement.setInt(7, player.getGame().getIdGame());
			statement.setInt(8, player.getIsBankrupt() == false ? 0 : 1);
			statement.setInt(9, player.getJailTurnsLeft());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Player findPlayerById(int id) {
		String sql = "SELECT * FROM Player WHERE id_player = ?";
		DAOManager daoManager = new DAOManager();
		ProfileDAO profileDAO = daoManager.getProfileDAO();
		CellDAO cellDAO = daoManager.getCellDAO();
		GameDAO gameDAO = daoManager.getGameDAO();

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statementCard = conn.prepareStatement(sql);
			statementCard.setInt(1, id);
			ResultSet resultSet = statementCard.executeQuery();
			if (resultSet.next()) {
				int playerId = resultSet.getInt("id_player");
				int profileId = resultSet.getInt("profile_id");
				int cellId = resultSet.getInt("cell_id");
				int money = resultSet.getInt("money");
				int gameId = resultSet.getInt("game_id");
				String cards = resultSet.getString("cards");
				String properties = resultSet.getString("properties");
				int isBankrupt = resultSet.getInt("is_bankrupt");
				int jailTurnsLeft = resultSet.getInt("jail_turns_left");

				Profile profile = profileDAO.findProfileById(profileId);
				Cell cell = cellDAO.findCellById(cellId);
				Game game = gameDAO.findGameById(gameId);

				if (profile != null || cell != null || game != null) {
					// TODO Arreglar lo de los List porque no se como hacerlo
					return new Player(playerId, profile, cell, money, cards, properties, game,
							isBankrupt == 0 ? false : true, jailTurnsLeft);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updatePlayer(Player player) {
		String sql = "UPDATE Player SET profile_id = ?, cell_id = ?, money = ?, cards = ?, properties = ?, game_id = ?, is_bankrupt = ?, jail_turns_left = ? WHERE id_player = ?";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, player.getProfile().getIdProfile());
			statement.setInt(2, player.getCell().getIdCell());
			statement.setInt(3, player.getMoney());
			statement.setString(4, player.getCards().toString());
			statement.setString(5, player.getProperties().toString());
			statement.setInt(6, player.getGame().getIdGame());
			statement.setInt(8, player.getIsBankrupt() == false ? 0 : 1);
			statement.setInt(8, player.getJailTurnsLeft());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletePlayer(int id) {
		String sql = "DELETE FROM Player WHERE id_player = ?";
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
	public List<Player> getAll() {
		List<Player> players = new ArrayList<Player>();
		String sql = "SELECT * FROM Player";

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			DAOManager daoManager = new DAOManager();
			ProfileDAO profileDAO = daoManager.getProfileDAO();
			CellDAO cellDAO = daoManager.getCellDAO();
			CardDAO cardDAO = daoManager.getCardDAO();
			PropertyDAO propertyDAO = daoManager.getPropertyDAO();
			GameDAO gameDAO = daoManager.getGameDAO();

			while (resultSet.next()) {
				int playerId = resultSet.getInt("id_player");
				int profileId = resultSet.getInt("profile_id");
				int cellId = resultSet.getInt("cell_id");
				int money = resultSet.getInt("money");
				int gameId = resultSet.getInt("game_id");
				int cardId = resultSet.getInt("card_id");
				int propertyId = resultSet.getInt("property_id");
				int isBankrupt = resultSet.getInt("is_bankrupt");
				int jailTurnsLeft = resultSet.getInt("jail_turns_left");

				Profile profile = profileDAO.findProfileById(profileId);
				Cell cell = cellDAO.findCellById(cellId);
				Card card = cardDAO.findCardById(cardId);
				Property property = propertyDAO.findPropertyById(propertyId);
				Game game = gameDAO.findGameById(gameId);

				if (profile != null || cell != null || card != null || property != null || game != null) {
					// TODO Arreglar lo de los List porque no se como hacerlo
					Player player = new Player(playerId, profile, cell, money, card, property, game,
							isBankrupt == 0 ? false : true, jailTurnsLeft);
					players.add(player);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return players;
	}

}
