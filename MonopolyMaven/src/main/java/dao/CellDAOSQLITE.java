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
import models.Cell.CellType;
import models.Property;

/**
 * @author Ana
 */
public class CellDAOSQLITE implements CellDAO {

	@Override
	public void addCell(Cell cell) {
		String sql = "INSERT INTO Cell(id_cell, type, card_id, property_id) VALUES (?, ?, ?, ?)";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, cell.getIdCell());
			statement.setString(2, cell.getType().name());
			statement.setInt(3, cell.getCard().getIdCard());
			statement.setInt(4, cell.getProperty().getIdProperty());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Cell findCellById(int id) {
		String sql = "SELECT * FROM Cell WHERE id_cell = ?";

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statementCard = conn.prepareStatement(sql);
			statementCard.setInt(1, id);
			ResultSet resultSet = statementCard.executeQuery();
			if (resultSet.next()) {
				int cellId = resultSet.getInt("id_cell");
				String typeString = resultSet.getString("type");
				int cardId = resultSet.getInt("card_id");
				int propertyId = resultSet.getInt("property_id");

				DAOManager daoManager = new DAOManager();
				CardDAO cardDAO = daoManager.getCardDAO();
				Card card = cardDAO.findCardById(cardId);

				PropertyDAO propertyDAO = daoManager.getPropertyDAO();
				Property property = propertyDAO.findPropertyById(propertyId);

				if (card != null || property != null) {
					return new Cell(cellId, CellType.valueOf(typeString), card, property);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateCell(Cell cell) {
		String sql = "UPDATE Cell SET type = ?, card_id = ?, property_id = ? WHERE id_cell = ?";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, cell.getType().name());
			statement.setInt(2, cell.getCard().getIdCard());
			statement.setInt(3, cell.getProperty().getIdProperty());
			statement.setInt(4, cell.getIdCell());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCell(int id) {
		String sql = "DELETE FROM Cell WHERE id_cell = ?";
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
	public List<Cell> getAll() {
		List<Cell> cells = new ArrayList<Cell>();
		String sql = "SELECT * FROM Cell";

		DAOManager daoManager = new DAOManager();
		CardDAO cardDAO = daoManager.getCardDAO();
		PropertyDAO propertyDAO = daoManager.getPropertyDAO();

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int cellId = resultSet.getInt("id_cell");
				String typeString = resultSet.getString("type");
				int cardId = resultSet.getInt("card_id");
				int propertyId = resultSet.getInt("property_id");

				Card card = cardDAO.findCardById(cardId);
				Property property = propertyDAO.findPropertyById(propertyId);

				if (card != null || property != null) {
					Cell cell = new Cell(cellId, CellType.valueOf(typeString), card, property);
					cells.add(cell);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cells;
	}

}
