package application.view;


import java.util.List;


import application.model.ParkingSpace;



public class ParkingSpaceView {
	
	
	public static int grid_width = 3;
	
	
	public static void DisplayParkingSpaces(List<ParkingSpace> parking_spaces){
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
	
	
}


