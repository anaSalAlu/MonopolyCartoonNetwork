package dao;

import java.util.List;

import models.Property;

/**
 * @author Ana
 */
public interface PropertyDAO {

	/* CRUD operations */
	/* Create */
	public void addProperty(Property property);

	/* Read */
	public Property findPropertyById(int id);

	/* Update */
	public void updateProperty(Property property);

	/* Delete */
	public void deleteProperty(int id);

	/* Read All */
	public List<Property> getAll();

}
