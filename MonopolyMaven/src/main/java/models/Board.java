package models;

import java.util.List;

/**
 * @author Ana
 */
public class Board {

	public int idBoard;
	public List<Cell> cells;
	public int size;

	public Board(int idBoard, List<Cell> cells, int size) {
		super();
		this.idBoard = idBoard;
		this.cells = cells;
		this.size = size;
	}

	public int getIdBoard() {
		return idBoard;
	}

	public void setIdBoard(int idBoard) {
		this.idBoard = idBoard;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String cellsToString() {
		StringBuilder sb = new StringBuilder();
		for (Cell cell : cells) {
			sb.append(cell.getIdCell()).append(",");
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	public static Cell[] stringToCells(String cellString, List<Cell> allCells) {
		String[] cellIds = cellString.split(",");
		Cell[] cells = new Cell[cellIds.length];

		for (int i = 0; i < cellIds.length; i++) {
			int id = Integer.parseInt(cellIds[i]);
			for (Cell cell : allCells) {
				if (cell.getIdCell() == id) {
					cells[i] = cell;
					break;
				}
			}
		}

		return cells;
	}
}
