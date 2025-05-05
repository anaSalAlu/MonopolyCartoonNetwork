package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Cell;
import models.Player;
import models.Property;

/**
 * @author Ana
 */
public class PropertyDAOSQLITE implements PropertyDAO {

	@Override
	public void addProperty(Property property) {
		String sql = "INSERT INTO Property(id_property, cell_id, sell_value, buy_value, house_buy_value, hotel_buy_value, "
				+ "rent_hotel_value, rent_house_value, rent_base_value, owner_id) VALUES (?, ?, ?, ? , ?, ?, ?, ?, ?, ?)";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, property.getIdProperty());
			statement.setInt(2, property.getCell().getIdCell());
			statement.setInt(4, property.getSellValue());
			statement.setInt(5, property.getBuyValue());
			statement.setInt(6, property.getHouseBuyValue());
			statement.setInt(7, property.getHotelBuyValue());
			statement.setInt(8, property.getRentHotelValue());
			// TODO mirar esto
			statement.setInt(9, property.getRentHouseValue());
			statement.setInt(10, property.getRentBaseValue());
			statement.setInt(11, property.getOwner().getIdPlayer());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Property findPropertyById(int id) {
		String sql = "SELECT * FROM Property WHERE id_property = ?";

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statementCard = conn.prepareStatement(sql);
			statementCard.setInt(1, id);
			ResultSet resultSet = statementCard.executeQuery();
			if (resultSet.next()) {
				int propertyId = resultSet.getInt("id_property");
				int cellId = resultSet.getInt("cell_id");
				int sellValue = resultSet.getInt("sell_value");
				int buyValue = resultSet.getInt("buy_value");
				int houseBuyValue = resultSet.getInt("house_buy_value");
				int hotelBuyValue = resultSet.getInt("hotel_buy_value");
				int rentHotelValue = resultSet.getInt("rent_hotel_value");
				int rentHouseValue = resultSet.getInt("rent_house_value");
				int rentBaseValue = resultSet.getInt("rent_base_value");
				int ownerId = resultSet.getInt("owner_id");

				DAOManager daoManager = new DAOManager();
				PlayerDAO playerDAO = daoManager.getPlayerDAO();
				Player player = playerDAO.findPlayerById(ownerId);

				CellDAO cellDAO = daoManager.getCellDAO();
				Cell cell = cellDAO.findCellById(cellId);

				if (player != null || cell != null) {
					// TODO mirar esto
					return new Property(propertyId, cell, sellValue, buyValue, houseBuyValue, hotelBuyValue,
							rentHotelValue, rentHouseValue, rentBaseValue, player);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateProperty(Property property) {
		String sql = "UPDATE Property SET cell_id = ?, sell_value = ?, buy_value = ?, house_buy_value = ?, hotel_buy_value = ?,"
				+ "rent_hotel_value = ?, rent_house_value = ?, rent_base_value = ?, owner_id = ? WHERE id_property = ?";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, property.getIdProperty());
			statement.setInt(2, property.getCell().getIdCell());
			statement.setInt(4, property.getSellValue());
			statement.setInt(5, property.getBuyValue());
			statement.setInt(6, property.getHouseBuyValue());
			statement.setInt(7, property.getHotelBuyValue());
			statement.setInt(8, property.getRentHotelValue());
			// TODO mirar esto
			statement.setInt(9, property.getRentHouseValue());
			statement.setInt(10, property.getRentBaseValue());
			statement.setInt(11, property.getOwner().getIdPlayer());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProperty(int id) {
		String sql = "DELETE FROM Property WHERE id_property = ?";
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
	public List<Property> getAll() {
		List<Property> properties = new ArrayList<Property>();
		String sql = "SELECT * FROM Property";

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			DAOManager daoManager = new DAOManager();
			PlayerDAO playerDAO = daoManager.getPlayerDAO();
			CellDAO cellDAO = daoManager.getCellDAO();

			while (resultSet.next()) {
				int propertyId = resultSet.getInt("id_property");
				int cellId = resultSet.getInt("cell_id");
				int sellValue = resultSet.getInt("sell_value");
				int buyValue = resultSet.getInt("buy_value");
				int houseBuyValue = resultSet.getInt("house_buy_value");
				int hotelBuyValue = resultSet.getInt("hotel_buy_value");
				int rentHotelValue = resultSet.getInt("rent_hotel_value");
				int rentHouseValue = resultSet.getInt("rent_house_value");
				int rentBaseValue = resultSet.getInt("rent_base_value");
				int ownerId = resultSet.getInt("owner_id");

				Player player = playerDAO.findPlayerById(ownerId);
				Cell cell = cellDAO.findCellById(cellId);

				if (player != null || cell != null) {
					// TODO mirar esto
					Property property = new Property(propertyId, cell, sellValue, buyValue, houseBuyValue,
							hotelBuyValue, rentHotelValue, rentHouseValue, rentBaseValue, player);
					properties.add(property);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return properties;
	}

}
