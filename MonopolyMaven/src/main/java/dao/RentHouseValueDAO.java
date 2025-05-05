package dao;

import java.util.List;

import models.RentHouseValue;

/**
 * @author Ana
 */
public interface RentHouseValueDAO {

	/* CRUD operations */
	/* Create */
	public void addRentHouseValue(RentHouseValue rentHouseValue);

	/* Read */
	public RentHouseValue findRentHouseValueById(int id);

	/* Update */
	public void updateRentHouseValue(RentHouseValue rentHouseValue);

	/* Delete */
	public void deleteRentHouseValue(int id);

	/* Read All */
	public List<RentHouseValue> getAll();

}
