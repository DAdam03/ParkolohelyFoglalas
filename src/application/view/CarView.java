package application.view;


import java.util.List;


import application.model.Car;



public class CarView {
	
	// @Note: Ez a metodus az auto adatait egy tablazat sorakent jeleniti meg.
	//        Ez csak akkor mukodik helyesen, ha a tablazat fejleceben az alabbi szoveg
	//        jelenik meg (mas esetben elcsuszhatnak az oszlopok):
	//        
	//        Rendszam | Hasznalhat mozgaskorlatozott parkolohelyet | Elektromos
	//        
	private static void DisplayCarRow(Car car) {
		System.out.println(car.GetLicensePlate() + "  | " + (car.GetCanUseHandicappedSpace() ? "Igen" : "Nem ") + "                                       | " + (car.GetCanUseElectricSpace() ? "Igen" : "Nem"));
	}
	
	private static void DisplayCarHeader() {
		System.out.println("Rendszam | Hasznalhat mozgaskorlatozott parkolohelyet | Elektromos");
	}
	
	
	
	public static void DisplayCar(Car car) {
		DisplayCarHeader();
		DisplayCarRow(car);
		System.out.println("");
	}
	
	
	public static void DisplayCars(List<Car> cars) {
		DisplayCarHeader();
		for(Car car : cars){
			DisplayCarRow(car);
		}
		System.out.println("");
	}
	
}






