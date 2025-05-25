package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Cell;
import models.Property;
import models.RentHouseValue;

/**
 * @author Ana
 */
public class PropertyDAOSQLITE implements PropertyDAO {

	@Override
	public void addProperty(Property property) {
		String sqlProperty = "INSERT INTO Property(id_property, cell_id, sell_value, buy_value, house_buy_value, hotel_buy_value, "
				+ "rent_hotel_value, rent_base_value) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = ManagerConnection.obtenirConnexio();
			statement = conn.prepareStatement(sqlProperty);
			statement.setInt(1, property.getIdProperty());
			statement.setInt(2, property.getCell().getIdCell());
			statement.setInt(3, property.getSellValue());
			statement.setInt(4, property.getBuyValue());
			statement.setInt(5, property.getHouseBuyValue());
			statement.setInt(6, property.getHotelBuyValue());
			statement.setInt(7, property.getRentHotelValue());
			statement.setInt(8, property.getRentBaseValue());
			statement.executeUpdate();

			if (property.getRentHouseValue() != null && !property.getRentHouseValue().isEmpty()) {
				DAOManager daoManager = new DAOManager();
				RentHouseValueDAO rentDAO = daoManager.getRentHouseValueDAO();

				for (RentHouseValue rent : property.getRentHouseValue()) {
					rentDAO.addRentHouseValue(rent);
				}
			}

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
	public Property findPropertyById(int id) {
		String sql = "SELECT * FROM Property WHERE id_property = ?";
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			conn = ManagerConnection.obtenirConnexio();
			statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int propertyId = resultSet.getInt("id_property");
				int cellId = resultSet.getInt("cell_id");
				int sellValue = resultSet.getInt("sell_value");
				int buyValue = resultSet.getInt("buy_value");
				int houseBuyValue = resultSet.getInt("house_buy_value");
				int hotelBuyValue = resultSet.getInt("hotel_buy_value");
				int rentHotelValue = resultSet.getInt("rent_hotel_value");
				int rentBaseValue = resultSet.getInt("rent_base_value");

				DAOManager daoManager = new DAOManager();
				CellDAO cellDAO = daoManager.getCellDAO();
				RentHouseValueDAO rentDAO = daoManager.getRentHouseValueDAO();

				Cell cell = cellDAO.findCellById(cellId);

				List<RentHouseValue> rentHouseValues = new ArrayList<>();
				for (int houseCount = 1; houseCount <= 3; houseCount++) {
					RentHouseValue rent = rentDAO.findRentValueByPropertyAndHouseCount(propertyId, houseCount);
					if (rent != null) {
						rentHouseValues.add(rent);
					}
				}

				return new Property(propertyId, cell, sellValue, buyValue, houseBuyValue, hotelBuyValue, rentHotelValue,
						rentHouseValues, rentBaseValue);
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
	public void updateProperty(Property property) {
		String sql = "UPDATE Property SET cell_id = ?, sell_value = ?, buy_value = ?, house_buy_value = ?, "
				+ "hotel_buy_value = ?, rent_hotel_value = ?, rent_base_value = ? WHERE id_property = ?";
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = ManagerConnection.obtenirConnexio();
			statement = conn.prepareStatement(sql);
			statement.setInt(1, property.getCell().getIdCell());
			statement.setInt(2, property.getSellValue());
			statement.setInt(3, property.getBuyValue());
			statement.setInt(4, property.getHouseBuyValue());
			statement.setInt(5, property.getHotelBuyValue());
			statement.setInt(6, property.getRentHotelValue());
			statement.setInt(7, property.getRentBaseValue());
			statement.setInt(8, property.getIdProperty());

			statement.executeUpdate();

			DAOManager daoManager = new DAOManager();
			RentHouseValueDAO rentDAO = daoManager.getRentHouseValueDAO();

			for (int houseCount = 1; houseCount <= 5; houseCount++) {
				rentDAO.deleteRentHouseValue(property.getIdProperty(), houseCount);
			}

			for (RentHouseValue rent : property.getRentHouseValue()) {
				rentDAO.addRentHouseValue(rent);
			}

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
	public void deleteProperty(int id) {
		String sql = "DELETE FROM Property WHERE id_property = ?";
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = ManagerConnection.obtenirConnexio();
			DAOManager daoManager = new DAOManager();
			RentHouseValueDAO rentDAO = daoManager.getRentHouseValueDAO();

			for (int houseCount = 1; houseCount <= 5; houseCount++) {
				rentDAO.deleteRentHouseValue(id, houseCount);
			}

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
	public List<Property> getAll() {
		List<Property> properties = new ArrayList<>();
		String sql = "SELECT * FROM Property";
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			conn = ManagerConnection.obtenirConnexio();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			DAOManager daoManager = new DAOManager();
			CellDAO cellDAO = daoManager.getCellDAO();
			RentHouseValueDAO rentDAO = daoManager.getRentHouseValueDAO();

			while (resultSet.next()) {
				int propertyId = resultSet.getInt("id_property");
				int cellId = resultSet.getInt("cell_id");
				int sellValue = resultSet.getInt("sell_value");
				int buyValue = resultSet.getInt("buy_value");
				int houseBuyValue = resultSet.getInt("house_buy_value");
				int hotelBuyValue = resultSet.getInt("hotel_buy_value");
				int rentHotelValue = resultSet.getInt("rent_hotel_value");
				int rentBaseValue = resultSet.getInt("rent_base_value");

				Cell cell = cellDAO.findCellById(cellId);

				List<RentHouseValue> rentHouseValues = new ArrayList<>();
				for (int houseCount = 1; houseCount <= 5; houseCount++) {
					RentHouseValue rent = rentDAO.findRentValueByPropertyAndHouseCount(propertyId, houseCount);
					if (rent != null) {
						rentHouseValues.add(rent);
					}
				}

				Property property = new Property(propertyId, cell, sellValue, buyValue, houseBuyValue, hotelBuyValue,
						rentHotelValue, rentHouseValues, rentBaseValue);
				properties.add(property);
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

		return properties;
	}

}
