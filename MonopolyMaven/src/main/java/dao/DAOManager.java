package dao;

/**
 * @author Ana
 */
public class DAOManager {

	private static ActionDAO actionDAO;
	private static CardDAO cardDAO;
	private static CellDAO cellDAO;
	private static GameDAO gameDAO;
	private static PlayerDAO playerDAO;
	private static ProfileDAO profileDAO;
	private static PropertyDAO propertyDAO;

	public static ActionDAO getActionDAO() {
		if (actionDAO == null) {
			actionDAO = new ActionDAOSQLITE();
		}
		return actionDAO;
	}

	public static CardDAO getCardDAO() {
		if (cardDAO == null) {
			cardDAO = new CardDAOSQLITE();
		}
		return cardDAO;
	}

	public static CellDAO getCellDAO() {
		if (cellDAO == null) {
			cellDAO = new CellDAOSQLITE();
		}
		return cellDAO;
	}

	public static GameDAO getGameDAO() {
		if (gameDAO == null) {
			gameDAO = new GameDAOSQLITE();
		}
		return gameDAO;
	}

	public static PlayerDAO getPlayerDAO() {
		if (playerDAO == null) {
			playerDAO = new PlayerDAOSQLITE();
		}
		return playerDAO;
	}

	public static ProfileDAO getProfileDAO() {
		if (profileDAO == null) {
			profileDAO = new ProfileDAOSQLITE();
		}
		return profileDAO;
	}

	public static PropertyDAO getPropertyDAO() {
		if (propertyDAO == null) {
			propertyDAO = new PropertyDAOSQLITE();
		}
		return propertyDAO;
	}
}
