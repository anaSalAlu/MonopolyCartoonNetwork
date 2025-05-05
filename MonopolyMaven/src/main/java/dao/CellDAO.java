package dao;

import java.util.List;

import models.Cell;

/**
 * @author Ana
 */
public interface CellDAO {

	/* CRUD operations */
	/* Create */
	public void addCell(Cell cell);

	/* Read */
	public Cell findCellById(int id);

	/* Update */
	public void updateCell(Cell cell);

	/* Delete */
	public void deleteCell(int id);

	/* Read All */
	public List<Cell> getAll();

}
