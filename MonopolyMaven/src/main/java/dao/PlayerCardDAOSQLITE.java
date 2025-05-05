package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.PlayerCard;

/**
 * @author Ana
 */
public class PlayerCardDAOSQLITE implements PlayerCardDAO {

	@Override
	public void addPlayerCard(PlayerCard playerCard) {
		String sql = "INSERT INTO PlayerCard(player_id, card_id, game_id) VALUES (?, ?, ?)";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setInt(1, playerCard.getPlayerId());
			statement.setInt(2, playerCard.getCardId());
			statement.setInt(3, playerCard.getGameId());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	// TODO mirar esto
	public PlayerCard findPlayerCardByPlayer(int id) {
		String sql = "SELECT * FROM PlayerCard WHERE id_action = ?";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updatePlayerCard(PlayerCard playerCard) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePlayerCard(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PlayerCard> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
