package models;

import java.util.List;

/**
 * @author Ana
 */
public class Property {

	public int idProperty;
	public Cell cell;
	public int sellValue;
	public int buyValue;
	public int houseBuyValue;
	public int hotelBuyValue;
	public int rentHotelValue;
	public List<Integer> rentHouseValue;
	public int rentBaseValue;

	public Property(int idProperty, Cell cell, int sellValue, int buyValue, int houseBuyValue, int hotelBuyValue,
			int rentHotelValue, List<Integer> rentHouseValue, int rentBaseValue) {
		super();
		this.idProperty = idProperty;
		this.cell = cell;
		this.sellValue = sellValue;
		this.buyValue = buyValue;
		this.houseBuyValue = houseBuyValue;
		this.hotelBuyValue = hotelBuyValue;
		this.rentHotelValue = rentHotelValue;
		this.rentHouseValue = rentHouseValue;
		this.rentBaseValue = rentBaseValue;
	}

	public int getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(int idProperty) {
		this.idProperty = idProperty;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public int getSellValue() {
		return sellValue;
	}

	public void setSellValue(int sellValue) {
		this.sellValue = sellValue;
	}

	public int getBuyValue() {
		return buyValue;
	}

	public void setBuyValue(int buyValue) {
		this.buyValue = buyValue;
	}

	public int getHouseBuyValue() {
		return houseBuyValue;
	}

	public void setHouseBuyValue(int houseBuyValue) {
		this.houseBuyValue = houseBuyValue;
	}

	public int getHotelBuyValue() {
		return hotelBuyValue;
	}

	public void setHotelBuyValue(int hotelBuyValue) {
		this.hotelBuyValue = hotelBuyValue;
	}

	public int getRentHotelValue() {
		return rentHotelValue;
	}

	public void setRentHotelValue(int rentHotelValue) {
		this.rentHotelValue = rentHotelValue;
	}

	public List<Integer> getRentHouseValue() {
		return rentHouseValue;
	}

	public void setRentHouseValue(List<Integer> rentHouseValue) {
		this.rentHouseValue = rentHouseValue;
	}

	public int getRentBaseValue() {
		return rentBaseValue;
	}

	public void setRentBaseValue(int rentBaseValue) {
		this.rentBaseValue = rentBaseValue;
	}
}
