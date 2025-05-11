package dao;

import models.RentHouseValue;

/**
 * @author Ana
 */
public interface RentHouseValueDAO {

	/* CRUD operations */
	/* Create */
	public void addRentHouseValue(RentHouseValue rentHouseValue);

	/* Update */
	public void updateRentHouseValue(RentHouseValue rentHouseValue);

	/* Delete */
	public void deleteRentHouseValue(int propertyId, int houseCount);

	/* Read All */
	public RentHouseValue findRentValueByPropertyAndHouseCount(int propertyId, int houseCount);

}
