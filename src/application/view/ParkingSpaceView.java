package application.view;


import java.util.List;


import application.controller.ParkingSpaceController;

import application.model.ParkingSpace;



public class ParkingSpaceView {
	
	
	public static int grid_width = 3;
	
	
	public static void DisplayParkingSpaces(List<ParkingSpace> parking_spaces) {
		System.out.println("[M]: Mozgaskorlatozott parkolohely");
		System.out.println("[E]: Elektromos autoknak fenntartott parkolohely");
		
		String output_string = "";
		for(int ind=0; ind<parking_spaces.size(); ++ind){
			if(ind != 0 && ind%grid_width == 0){
				System.out.println(output_string);
				output_string = "";
			}
			
			output_string += parking_spaces.get(ind).GetType() == ParkingSpace.ParkingSpaceType.HANDICAPPED ? "[M] " : parking_spaces.get(ind).GetType() == ParkingSpace.ParkingSpaceType.ELECTRIC ? "[E] " : "[ ] ";
		}
		System.out.println(output_string);
		System.out.println("");
	}
	
	// @Note: A tomb elso eleme a parkolohely oszlopa, a masodik a sora.
	public static int[] GetParkingSpaceColumnAndRow(ParkingSpace parking_space) {
		int[] result = new int[2];
		result[0] = 1;
		result[1] = 1;
		
		ParkingSpaceController parking_space_controller = ParkingSpaceController.GetInstance();
		List<ParkingSpace> parking_spaces = parking_space_controller.ListParkingSpaces();
		
		for(int ind=0; ind<parking_spaces.size(); ++ind){
			if(parking_spaces.get(ind).GetId() == parking_space.GetId()){
				result[0] = ind%grid_width + 1;
				result[1] = ind/grid_width + 1;
				break;
			}
		}
		
		return result;
	}
	
	
	public static ParkingSpace GetParkingSpaceByColumnAndRow(int column, int row) {
		ParkingSpace result = null;
		
		ParkingSpaceController parking_space_controller = ParkingSpaceController.GetInstance();
		List<ParkingSpace> parking_spaces = parking_space_controller.ListParkingSpaces();
		
		int index = row*grid_width + column;
		if(column < grid_width && column > -1 && row > -1 && index < parking_spaces.size()){
			result = parking_spaces.get(index);
		}
		
		return result;
	}
	
	
}


