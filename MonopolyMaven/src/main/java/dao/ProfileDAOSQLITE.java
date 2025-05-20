package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Profile;

/**
 * @author Ana
 */
public class ProfileDAOSQLITE implements ProfileDAO {

	@Override
	public void addProfile(Profile profile) {
		String sql = "INSERT INTO Profile(id_profile, nickname, image) VALUES (?, ?, ?)";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, profile.getIdProfile());
			statement.setString(2, profile.getNickname());
			statement.setString(3, profile.getImage());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Profile findProfileById(int id) {
		String sqlCard = "SELECT * FROM Profile WHERE id_profile = ?";

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sqlCard);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int profileId = resultSet.getInt("id_card");
				String nickname = resultSet.getString("nickname");
				String image = resultSet.getString("image");

				return new Profile(profileId, nickname, image);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateProfile(Profile profile) {
		String sql = "UPDATE Profile SET nickname = ?, image = ? WHERE id_profile = ?";
		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, profile.getNickname());
			statement.setString(2, profile.getImage());
			statement.setInt(3, profile.getIdProfile());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProfile(int id) {
		String sql = "DELETE FROM Profile WHERE id_profile = ?";
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
	public List<Profile> getAll() {
		List<Profile> profiles = new ArrayList<Profile>();
		String sql = "SELECT * FROM Profile";

		try {
			Connection conn = ManagerConnection.obtenirConnexio();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int profileId = resultSet.getInt("id_profile");
				String nickname = resultSet.getString("nickname");
				String image = resultSet.getString("image");

				Profile profile = new Profile(profileId, nickname, image);
				profiles.add(profile);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profiles;
	}

}
