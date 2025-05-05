package models;

public class RentHouseValue {

	private int propertyId;
	private int houseCount;
	private int rentValue;

	public RentHouseValue(int propertyId, int houseCount, int rentValue) {
		super();
		this.propertyId = propertyId;
		this.houseCount = houseCount;
		this.rentValue = rentValue;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getHouseCount() {
		return houseCount;
	}

	public void setHouseCount(int houseCount) {
		this.houseCount = houseCount;
	}

	public int getRentValue() {
		return rentValue;
	}

	public void setRentValue(int rentValue) {
		this.rentValue = rentValue;
	}

}
