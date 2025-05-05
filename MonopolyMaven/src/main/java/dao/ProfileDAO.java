package dao;

import java.util.List;

import models.Profile;

/**
 * @author Ana
 */
public interface ProfileDAO {

	/* CRUD operations */
	/* Create */
	public void addProfile(Profile profile);

	/* Read */
	public Profile findProfileById(int id);

	/* Update */
	public void updateProfile(Profile profile);

	/* Delete */
	public void deleteProfile(int id);

	/* Read All */
	public List<Profile> getAll();

}
