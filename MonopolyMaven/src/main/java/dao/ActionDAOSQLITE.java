package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Action;
import models.Action.Type;

/**
 * @author Ana
 */
public class ActionDAOSQLITE implements ActionDAO {

	@Override
	public void addAction(Action action) {
		String sql = "INSERT INTO Action(id_action, action_type, times) VALUES (?, ?, ?)";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setInt(1, action.getIdAction());
			statement.setString(2, action.getActionType().name());
			statement.setInt(3, action.getTimes());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Action findActionById(int id) {
		String sql = "SELECT * FROM Action WHERE id_action = ?";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int actionId = resultSet.getInt("id_action");
				String typeString = resultSet.getString("action_type");
				int times = resultSet.getInt("times");
				return new Action(actionId, Type.valueOf(typeString), times);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateAction(Action action) {
		String sql = "UPDATE Action SET action_type = ?, times = ? WHERE id_action = ?";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setString(1, action.getActionType().name());
			statement.setInt(2, action.getTimes());
			statement.setInt(3, action.getIdAction());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAction(int id) {
		String sql = "DELETE FROM Action WHERE id_action = ?";
		try (Connection conn = ManagerConnection.obtenirConnexio();
				PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setInt(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Action> getAll() {
		List<Action> actions = new ArrayList<>();
		String sql = "SELECT * FROM Action";

		try (Connection conn = ManagerConnection.obtenirConnexio();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				int actionId = resultSet.getInt("id_action");
				String typeString = resultSet.getString("action_type");
				int times = resultSet.getInt("times");
				Action action = new Action(actionId, Type.valueOf(typeString), times);

				actions.add(action);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actions;
	}

}