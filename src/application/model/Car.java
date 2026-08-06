package application.model;


public class Car {
	
	int id;
	
	boolean can_use_handicapped_space;
	boolean can_use_electric_space;
	
	public Car(int id, boolean can_use_handicapped_space, boolean can_use_electric_space) {
		this.id                        = id;
		this.can_use_handicapped_space = can_use_handicapped_space;
		this.can_use_electric_space    = can_use_electric_space;
	}
	
	
}


