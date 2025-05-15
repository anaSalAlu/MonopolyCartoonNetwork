package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import table.queries.TableQueries;

/**
 * @author Ana
 */
public class ManagerConnection {

	// Creamos la ruta donde se va a guardar la base de datos
	// en este caso la guardamos en carpeta de usuario/db
	private static String dbFolder = System.getProperty("user.dir") + "/db/MonopolyCartoonNetwork.db";

	// Crea la base de datos si NO existe
	private static Connection connexio = null;

	private static int connectar() {
		try {
			// Antes de crear la conexión, miramos si la carpeta existe
			File carpeta = new File(System.getProperty("user.dir") + "/db");

			// Si no existe, entonces creamos la carpeta
			if (!carpeta.exists()) {
				carpeta.mkdirs();
			}

			connexio = DriverManager.getConnection("jdbc:sqlite:" + dbFolder);
			createTables();
			insertDefaultData();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static boolean isConnected() throws SQLException {
		if (connexio == null || connexio.isClosed()) {
			return false;
		} else {
			return true;
		}
	}

	public static Connection obtenirConnexio() {
		try {
			if (!isConnected()) {
				connectar();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connexio;
	}

	public static void tancarConnexio() {
		try {
			if (connexio != null && !connexio.isClosed()) {
				connexio.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Aquí irán los sql de la creación de las tablas, luego lo llamaremos en la
	// conexión y creará las tablas
	private static void createTables() {
		try (Connection conn = obtenirConnexio()) {
			java.sql.Statement smt = conn.createStatement();
			smt.execute(TableQueries.SQL_ACTION);
			smt.execute(TableQueries.SQL_CARD);
			smt.execute(TableQueries.SQL_CELL);
			smt.execute(TableQueries.SQL_GAME);
			smt.execute(TableQueries.SQL_PLAYER);
			smt.execute(TableQueries.SQL_PROFILE);
			smt.execute(TableQueries.SQL_PROPERTY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void insertDefaultData() {

	}

}
