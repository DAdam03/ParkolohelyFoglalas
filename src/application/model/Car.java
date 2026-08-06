package application.model;


public class Car {
	
	int id;
	String license_plate;
	
	boolean can_use_handicapped_space;
	boolean can_use_electric_space;
	
	public Car(int id, String license_plate, boolean can_use_handicapped_space, boolean can_use_electric_space) {
		this.id                        = id;
		this.license_plate             = license_plate;
		this.can_use_handicapped_space = can_use_handicapped_space;
		this.can_use_electric_space    = can_use_electric_space;
	}
	
	
	public String GetLicensePlate() {
		return this.license_plate;
	}
	
	public boolean GetCanUseHandicappedSpace() {
		return this.can_use_handicapped_space;
	}
	
	public boolean GetCanUseElectricSpace() {
		return this.can_use_electric_space;
	}
	
}


