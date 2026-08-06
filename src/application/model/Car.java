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
	
	
}


