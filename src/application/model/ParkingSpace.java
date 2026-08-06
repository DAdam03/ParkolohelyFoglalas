package application.model;


public class ParkingSpace {

	enum ParkingSpaceType {
		NORMAL,
		HANDICAPPED,
		ELECTRIC
	}
	
	int id;
	ParkingSpaceType type;
	
	public ParkingSpace(int id, ParkingSpaceType type) {
		this.id   = id;
		this.type = type;
	}
	
	
}


