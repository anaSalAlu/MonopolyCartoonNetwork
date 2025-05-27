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
	private static PlayerCardDAO playerCardDAO;
	private static PlayerPropertyDAO playerPropertyDAO;
	private static RentHouseValueDAO rentHouseValueDAO;

	public ActionDAO getActionDAO() {
		if (actionDAO == null) {
			actionDAO = new ActionDAOSQLITE();
		}
		return actionDAO;
	}

	public CardDAO getCardDAO() {
		if (cardDAO == null) {
			cardDAO = new CardDAOSQLITE();
		}
		return cardDAO;
	}

	public CellDAO getCellDAO() {
		if (cellDAO == null) {
			cellDAO = new CellDAOSQLITE();
		}
		return cellDAO;
	}

	public GameDAO getGameDAO() {
		if (gameDAO == null) {
			gameDAO = new GameDAOSQLITE();
		}
		return gameDAO;
	}

	public PlayerDAO getPlayerDAO() {
		if (playerDAO == null) {
			playerDAO = new PlayerDAOSQLITE();
		}
		return playerDAO;
	}

	public ProfileDAO getProfileDAO() {
		if (profileDAO == null) {
			profileDAO = new ProfileDAOSQLITE();
		}
		return profileDAO;
	}

	public PropertyDAO getPropertyDAO() {
		if (propertyDAO == null) {
			propertyDAO = new PropertyDAOSQLITE();
		}
		return propertyDAO;
	}

	public PlayerCardDAO getPlayerCardDAO() {
		if (playerCardDAO == null) {
			playerCardDAO = new PlayerCardDAOSQLITE();
		}
		return playerCardDAO;
	}

	public PlayerPropertyDAO getPlayerPropertyDAO() {
		if (playerPropertyDAO == null) {
			playerPropertyDAO = new PlayerPropertyDAOSQLITE();
		}
		return playerPropertyDAO;
	}

	public RentHouseValueDAO getRentHouseValueDAO() {
		if (rentHouseValueDAO == null) {
			rentHouseValueDAO = new RentHouseValueDAOSQLITE();
		}
		return rentHouseValueDAO;
	}
}
