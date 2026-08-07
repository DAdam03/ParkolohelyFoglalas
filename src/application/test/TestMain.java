package application.test;



public class TestMain{
	
	public static void RunTests(){
		
		TestCarDAO test_car_dao = new TestCarDAO();
		test_car_dao.TestAll();
		
		TestParkingSpaceDAO test_parking_space_dao = new TestParkingSpaceDAO();
		test_parking_space_dao.TestAll();
		
		TestReservationDAO test_reservation_dao = new TestReservationDAO();
		test_reservation_dao.TestAll();
		
		
		TestCarController test_car_controller = new TestCarController();
		test_car_controller.TestAll();
		
		TestReservationController test_reservation_controller = new TestReservationController();
		test_reservation_controller.TestAll();
		
		
		System.out.println("End of tests");
		System.out.println("");
		
	}
	
	
}



