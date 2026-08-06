package application.model;


public class ParkingSpace {

	public enum ParkingSpaceType {
		NORMAL(0),
		HANDICAPPED(1),
		ELECTRIC(2);
		
		private final int index;   

		ParkingSpaceType(int index) {
			this.index = index;
		}

		public int index() { 
			return index;
		}
	}
	
	int id;
	ParkingSpaceType type;
	
	public ParkingSpace(int id, ParkingSpaceType type) {
		this.id   = id;
		this.type = type;
	}
	
	public ParkingSpace(int id, int type) {
		ParkingSpaceType converted_type = ParkingSpaceType.NORMAL;
		for (ParkingSpaceType parking_space_type : ParkingSpaceType.values()) {
			if(parking_space_type.index() == type){
				converted_type = parking_space_type;
				break;
			}
		}
		
		this.id = id;
		this.type = converted_type;
	}
	
	
	public int GetId(){
		return this.id;
	}
	
	public ParkingSpaceType GetType(){
		return this.type;
	}
}


