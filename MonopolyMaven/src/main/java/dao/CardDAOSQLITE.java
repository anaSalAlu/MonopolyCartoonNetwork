package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Action;
import models.Card;

/**
 * @author Ana
 */
public class CardDAOSQLITE implements CardDAO {

	@Override
	public void addCard(Card card) {
		String sql = "INSERT INTO Card(id_card, type, action_id) VALUES (?, ?, ?)";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, card.getIdCard());
			statement.setString(2, card.getType().name());
			statement.setInt(3, card.getAction().getIdAction());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Card findCardById(int id) {
		String sqlCard = "SELECT * FROM Card WHERE id_card = ?";

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statementCard = conn.prepareStatement(sqlCard);
			statementCard.setInt(1, id);
			ResultSet resultSetCard = statementCard.executeQuery();
			if (resultSetCard.next()) {
				int cardId = resultSetCard.getInt("id_card");
				String typeString = resultSetCard.getString("type");
				Card.CardType type = Card.CardType.valueOf(typeString);
				int actionId = resultSetCard.getInt("action_id");

				DAOManager daoManager = new DAOManager();
				ActionDAO actionDAO = daoManager.getActionDAO();
				Action action = actionDAO.findActionById(actionId);

				if (action != null) {
					return new Card(cardId, type, action);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateCard(Card card) {
		String sql = "UPDATE Card SET type = ?, action_id = ? WHERE id_card = ?";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, card.getType().name());
			statement.setInt(2, card.getAction().getIdAction());
			statement.setInt(3, card.getIdCard());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCard(int id) {
		String sql = "DELETE FROM Card WHERE id_card = ?";
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
	public List<Card> getAll() {
		List<Card> cards = new ArrayList<Card>();
		String sql = "SELECT * FROM Card";

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			DAOManager daoManager = new DAOManager();
			ActionDAO actionDAO = daoManager.getActionDAO();

			while (resultSet.next()) {
				int cardId = resultSet.getInt("id_card");
				String typeString = resultSet.getString("type");
				Card.CardType type = Card.CardType.valueOf(typeString);
				int actionId = resultSet.getInt("action_id");

				Action action = actionDAO.findActionById(actionId);

				if (action != null) {
					Card card = new Card(cardId, type, action);
					cards.add(card);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cards;
	}

}
