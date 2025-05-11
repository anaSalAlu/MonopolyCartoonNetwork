package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.RentHouseValue;

public class RentHouseValueDAOSQLITE implements RentHouseValueDAO {

	@Override
	public void addRentHouseValue(RentHouseValue rentHouseValue) {
		String sql = "INSERT INTO Rent_House_Value(property_id, house_count, rent_value) VALUES (?, ?, ?)";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setInt(1, rentHouseValue.getPropertyId());
			statement.setInt(2, rentHouseValue.getHouseCount());
			statement.setInt(3, rentHouseValue.getRentValue());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateRentHouseValue(RentHouseValue rentHouseValue) {
		String sql = "UPDATE Rent_House_Value SET rent_value = ? WHERE property_id = ? AND house_count = ?";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setInt(1, rentHouseValue.getRentValue());
			statement.setInt(2, rentHouseValue.getPropertyId());
			statement.setInt(3, rentHouseValue.getHouseCount());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRentHouseValue(int propertyId, int houseCount) {
		String sql = "DELETE FROM Rent_House_Value WHERE property_id = ? AND house_count = ?";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setInt(1, propertyId);
			statement.setInt(2, houseCount);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public RentHouseValue findRentValueByPropertyAndHouseCount(int propertyId, int houseCount) {
		String sql = "SELECT * FROM Rent_House_Value WHERE property_id = ? AND house_count = ?";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setInt(1, propertyId);
			statement.setInt(2, houseCount);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int rentValue = resultSet.getInt("rent_value");
				return new RentHouseValue(propertyId, houseCount, rentValue);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
