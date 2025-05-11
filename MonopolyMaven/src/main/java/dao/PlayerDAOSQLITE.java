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
import models.PlayerCard;
import models.PlayerProperty;
import models.Profile;
import models.Property;

/**
 * @author Ana
 */
public class PlayerDAOSQLITE implements PlayerDAO {

	@Override
	public void addPlayer(Player player) {
		String sql = "INSERT INTO Player(id_player, profile_id, cell_id, money, game_id, is_bankrupt, jail_turns_left) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, player.getIdPlayer());
			stmt.setInt(2, player.getProfile().getIdProfile());
			stmt.setInt(3, player.getCell().getIdCell());
			stmt.setInt(4, player.getMoney());
			stmt.setInt(5, player.getGame().getIdGame());
			stmt.setInt(6, player.getIsBankrupt() ? 1 : 0);
			stmt.setInt(7, player.getJailTurnsLeft());
			stmt.executeUpdate();

			DAOManager daoManager = new DAOManager();
			PlayerCardDAO cardDAO = daoManager.getPlayerCardDAO();
			PlayerPropertyDAO propertyDAO = daoManager.getPlayerPropertyDAO();

			for (Card card : player.getCards()) {
				cardDAO.addPlayerCard(
						new PlayerCard(player.getIdPlayer(), card.getIdCard(), player.getGame().getIdGame()));
			}

			for (Property property : player.getProperties()) {
				propertyDAO.addPlayerProperty(new PlayerProperty(player.getIdPlayer(), property.getIdProperty(),
						player.getGame().getIdGame()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Player findPlayerById(int id) {
		String sql = "SELECT * FROM Player WHERE id_player = ?";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int profileId = rs.getInt("profile_id");
				int cellId = rs.getInt("cell_id");
				int money = rs.getInt("money");
				int gameId = rs.getInt("game_id");
				boolean isBankrupt = rs.getInt("is_bankrupt") == 1;
				int jailTurns = rs.getInt("jail_turns_left");

				DAOManager daoManager = new DAOManager();
				ProfileDAO profileDAO = daoManager.getProfileDAO();
				CellDAO cellDAO = daoManager.getCellDAO();
				GameDAO gameDAO = daoManager.getGameDAO();
				CardDAO cardDAO = daoManager.getCardDAO();
				PlayerCardDAO playerCardDAO = daoManager.getPlayerCardDAO();
				PropertyDAO propertyDAO = daoManager.getPropertyDAO();
				PlayerPropertyDAO playerPropertyDAO = daoManager.getPlayerPropertyDAO();

				Profile profile = profileDAO.findProfileById(profileId);
				Cell cell = cellDAO.findCellById(cellId);
				Game game = gameDAO.findGameById(gameId);

				// Obtener cartas
				List<Card> cards = new ArrayList<>();
				for (PlayerCard pc : playerCardDAO.findPlayerCardsByPlayerAndGame(id, gameId)) {
					cards.add(cardDAO.findCardById(pc.getCardId()));
				}

				// Obtener propiedades
				List<Property> properties = new ArrayList<>();
				for (PlayerProperty pp : playerPropertyDAO.findPlayerPropertiesByPlayerAndGame(id, gameId)) {
					properties.add(propertyDAO.findPropertyById(pp.getPropertyId()));
				}

				Player player = new Player();
				player.setIdPlayer(id);
				player.setProfile(profile);
				player.setCell(cell);
				player.setMoney(money);
				player.setGame(game);
				player.setBankrupt(isBankrupt);
				player.setJailTurnsLeft(jailTurns);
				player.setCards(cards);
				player.setProperties(properties);
				return player;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updatePlayer(Player player) {
		String sql = "UPDATE Player SET profile_id = ?, cell_id = ?, money = ?, game_id = ?, is_bankrupt = ?, jail_turns_left = ? WHERE id_player = ?";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, player.getProfile().getIdProfile());
			stmt.setInt(2, player.getCell().getIdCell());
			stmt.setInt(3, player.getMoney());
			stmt.setInt(4, player.getGame().getIdGame());
			stmt.setInt(5, player.getIsBankrupt() ? 1 : 0);
			stmt.setInt(6, player.getJailTurnsLeft());
			stmt.setInt(7, player.getIdPlayer());
			stmt.executeUpdate();

			DAOManager daoManager = new DAOManager();
			PlayerCardDAO cardDAO = daoManager.getPlayerCardDAO();
			PlayerPropertyDAO propertyDAO = daoManager.getPlayerPropertyDAO();

			cardDAO.deletePlayerCard(player.getIdPlayer(), player.getGame().getIdGame(),
					player.getCards().get(0).getIdCard());
			for (Card card : player.getCards()) {
				cardDAO.addPlayerCard(
						new PlayerCard(player.getIdPlayer(), card.getIdCard(), player.getGame().getIdGame()));
			}

			propertyDAO.deletePlayerProperty(player.getIdPlayer(), player.getGame().getIdGame(),
					player.getProperties().get(0).getIdProperty());
			for (Property property : player.getProperties()) {
				propertyDAO.addPlayerProperty(new PlayerProperty(player.getIdPlayer(), property.getIdProperty(),
						player.getGame().getIdGame()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletePlayer(int id) {
		String sql = "DELETE FROM Player WHERE id_player = ?";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Player> getAll() {
		List<Player> players = new ArrayList<>();
		String sql = "SELECT * FROM Player";

		try (Connection conn = ManagerConnection.obtenirConnexio();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Player player = findPlayerById(rs.getInt("id_player"));
				if (player != null) {
					players.add(player);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return players;
	}

}
