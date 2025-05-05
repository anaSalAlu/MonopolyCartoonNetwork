package dao;

import java.util.List;

import models.Action;

/**
 * @author Ana
 */
public interface ActionDAO {

	/* CRUD operations */
	/* Create */
	public void addAction(Action action);

	/* Read */
	public Action findActionById(int id);

	/* Update */
	public void updateAction(Action action);

	/* Delete */
	public void deleteAction(int id);

	/* Read All */
	public List<Action> getAll();
}
