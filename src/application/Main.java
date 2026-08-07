package application;


import java.util.Scanner;

import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import application.dao.DatabaseInitializer;

import application.controller.CarController;
import application.controller.ParkingSpaceController;
import application.controller.ReservationController;

import application.view.CarView;
import application.view.ParkingSpaceView;
import application.view.ReservationView;

import application.model.Car;
import application.model.ParkingSpace;
import application.model.Reservation;

import application.test.TestMain;


public class Main {
	
	enum Command {
		BEJELENTKEZES,            B,
		KIJELENTKEZES,            K,
		
		ELRENDEZES_MODOSITASA,    EM,
		
		PARKOLOHELYEK_MODOSITASA, PHM,
		PARKOLOHELYEK_LISTAZASA,  PHL,
		
		AUTOK_LISTAZASA,          AL,
		AUTO_TORLESE,             AT,
		UJ_AUTO,                  UA,
		
		FOGLALASOK_LISTAZASA,     FL,
		FOGLALAS,                 F,
		FOGLALAS_TORLESE,         FT,
		SUGO,
		HIBAS;
	}
	
	public static Car user           = null;
	public static boolean admin_user = false;
	
	public static boolean run_tests  = false;
	
	
	public static void main(String[] args){
		
		try {
			
			DatabaseInitializer.InitializeDatabase();
			
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		
		
		if(run_tests){
			TestMain.RunTests();
		}
		
		
		CarController car_controller = CarController.GetInstance();
		ParkingSpaceController parking_space_controller = ParkingSpaceController.GetInstance();
		ReservationController reservation_controller = ReservationController.GetInstance();
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		Scanner scanner = new Scanner(System.in);
		
		
		
		System.out.println("Parkolohely foglalo rendszer");
		System.out.println("A lehetseges parancsok megjelenitesehez hasznalja a 'sugo' parancsot!");
		
		
		String command = scanner.nextLine().strip();
		String[] command_args = command.split(" ");
		command_args[0] = command_args[0].toUpperCase();
		while(!command_args[0].equals("KILEPES")){
			Command converted_command = Command.HIBAS;
			
			try{
				converted_command = Command.valueOf(command_args[0]);
			}catch(Exception e) {}
			
			switch(converted_command){
				case B:
				case BEJELENTKEZES: {
					if(command_args.length == 2){
						if(user == null && !admin_user){
							String upper_case_arg = command_args[1].toUpperCase();
							if(upper_case_arg.equals("ADMIN")){
								admin_user = true;
								
								System.out.println("Sikeres bejelentkezes!");
							}else{
								Car new_user = car_controller.GetCarByLicensePlate(command_args[1]);
								if(new_user != null){
									user = new_user;
									System.out.println("Sikeres bejelentkezes!");
								}else{
									System.out.println("Sikertelen bejelentkezes! Nincs ilyen rendszamu auto a rendszerben.");
								}
							}
						}else{
							System.out.println("Már be van jelentkezve "+ (admin_user ? "az adminisztrator felhasznaloval." : "a "+user.GetLicensePlate()+" rendszamu autoval."));
							System.out.println("Ha ki szeretne jelentkezni, hasznalja a 'kijelentkezes' parancsot!");
						}
					}else{
						System.out.println("A 'bejelentkezes' parancsnak nem "+(command_args.length-1)+" parametere van.");
						System.out.println("A parancs helyes hasznalata:");
						System.out.println("bejelentkezes rendszam|admin");
					}
				} break;
				
				case K:
				case KIJELENTKEZES: {
					if(command_args.length == 1){
						if(user != null || admin_user){
							user = null;
							admin_user = false;
						}else{
							System.out.println("Meg nincs bejelentkezve.");
							System.out.println("Ha be szeretne jelentkezni, hasznalja a 'bejelentkezes' parancsot!");
						}
					}else{
						System.out.println("A 'kijelentkezes' parancsnak nem "+(command_args.length-1)+" parametere van.");
						System.out.println("A parancs helyes hasznalata:");
						System.out.println("kijelentkezes");
					}
				} break;
				
				case EM:
				case ELRENDEZES_MODOSITASA: {
					if(command_args.length == 2){
						if(admin_user){
							try{
								int column_count = Integer.parseInt(command_args[1]);
								if(column_count < 1){
									column_count = 1;
								}
								
								ParkingSpaceView.grid_width = column_count;
								
								System.out.println("Az oszlopok szama sikeresen modositva erre: '"+column_count+"'.");
								
							}catch(Exception e) {
								System.out.println("'"+command_args[1]+"' nem szam.");
								System.out.println("A parancs helyes hasznalata:");
								System.out.println("elrendezes_modositasa oszlopok_szama");
							}
						}else{
							System.out.println("Ezt a parancsot csak adminisztratorok tudjak hasznalni.");
							System.out.println("Ha be szeretne jelentkezni adminisztratorkent, hasznalja a 'bejelentkezes' parancsot!");
						}
					}else{
						System.out.println("Az 'elrendezes_modositasa' parancsnak nem "+(command_args.length-1)+" parametere van.");
						System.out.println("A parancs helyes hasznalata:");
						System.out.println("elrendezes_modositasa oszlopok_szama");
					}
				} break;
				
				case PHM:
				case PARKOLOHELYEK_MODOSITASA: {
					if(command_args.length == 4){
						if(admin_user){
							int normal_spaces      = 0;
							int handicapped_spaces = 0;
							int electric_spaces    = 0;
							
							boolean correct_data = true;
							
							try{
								normal_spaces = Integer.parseInt(command_args[1]);
								if(normal_spaces < 0){
									normal_spaces = 1;
								}
							}catch(Exception e){
								System.out.println("'"+command_args[1]+"' nem szam.");
								correct_data = false;
							}
							
							try{
								handicapped_spaces = Integer.parseInt(command_args[2]);
								if(handicapped_spaces < 0){
									handicapped_spaces = 1;
								}
							}catch(Exception e){
								System.out.println("'"+command_args[2]+"' nem szam.");
								correct_data = false;
							}
							
							try{
								electric_spaces = Integer.parseInt(command_args[3]);
								if(electric_spaces < 0){
									electric_spaces = 1;
								}
							}catch(Exception e){
								System.out.println("'"+command_args[3]+"' nem szam.");
								correct_data = false;
							}
							
							if(correct_data){
								List<ParkingSpace> parking_spaces = parking_space_controller.ListParkingSpaces();
								
								for(ParkingSpace parking_space : parking_spaces){
									switch(parking_space.GetType()){
										case NORMAL: {
											if(normal_spaces > 0){
												--normal_spaces;
											}else{
												parking_space_controller.DeleteParkingSpace(parking_space.GetId());
											}
										} break;
										
										case HANDICAPPED: {
											if(handicapped_spaces > 0){
												--handicapped_spaces;
											}else{
												parking_space_controller.DeleteParkingSpace(parking_space.GetId());
											}
										} break;
										
										case ELECTRIC: {
											if(electric_spaces > 0){
												--electric_spaces;
											}else{
												parking_space_controller.DeleteParkingSpace(parking_space.GetId());
											}
										} break;
									}
								}
								
								for(int i=0; i<normal_spaces; ++i){
									parking_space_controller.AddParkingSpace(ParkingSpace.ParkingSpaceType.NORMAL);
								}
								
								for(int i=0; i<handicapped_spaces; ++i){
									parking_space_controller.AddParkingSpace(ParkingSpace.ParkingSpaceType.HANDICAPPED);
								}
								
								for(int i=0; i<electric_spaces; ++i){
									parking_space_controller.AddParkingSpace(ParkingSpace.ParkingSpaceType.ELECTRIC);
								}
								
								System.out.println("Parkolohelyek modositasa sikeres!");
								System.out.println("Jelenlegi parkolohelyek:");
								
								parking_spaces = parking_space_controller.ListParkingSpaces();
								
								ParkingSpaceView.DisplayParkingSpaces(parking_spaces);
								
							}else{
								System.out.println("A parancs helyes hasznalata:");
								System.out.println("parkolohelyek_modositasa egyszeru_helyek_szama mozgaskorlatozott_helyek_szama elektromos_helyek_szama");
							}
							
						}else{
							System.out.println("Ezt a parancsot csak adminisztratorok tudjak hasznalni.");
							System.out.println("Ha be szeretne jelentkezni adminisztratorkent, hasznalja a 'bejelentkezes' parancsot!");
						}
					}else{
						System.out.println("A 'parkolohelyek_modositasa' parancsnak nem "+(command_args.length-1)+" parametere van.");
						System.out.println("A parancs helyes hasznalata:");
						System.out.println("parkolohelyek_modositasa egyszeru_helyek_szama mozgaskorlatozott_helyek_szama elektromos_helyek_szama");
					}
				} break;
				
				case PHL:
				case PARKOLOHELYEK_LISTAZASA: {
					if(command_args.length == 1){
						List<ParkingSpace> parking_spaces = parking_space_controller.ListParkingSpaces();
						ParkingSpaceView.DisplayParkingSpaces(parking_spaces);
					}else{
						System.out.println("Az 'parkolohelyek_listazasa' parancsnak nem "+(command_args.length-1)+" parametere van.");
						System.out.println("A parancs helyes hasznalata:");
						System.out.println("parkolohelyek_listazasa");
					}
				} break;
				
				case AL:
				case AUTOK_LISTAZASA: {
					if(command_args.length == 1){
						if(admin_user){
							List<Car> cars = car_controller.ListCars();
							CarView.DisplayCars(cars);
						}else if(user != null){
							CarView.DisplayCar(user);
						}else{
							System.out.println("Az autok listazasasahoz be kell jelentkezni.");
							System.out.println("Ha be szeretne jelentkezni, hasznalja a 'bejelentkezes' parancsot!");
						}
					}else{
						System.out.println("Az 'autok_listazasa' parancsnak nem "+(command_args.length-1)+" parametere van.");
						System.out.println("A parancs helyes hasznalata:");
						System.out.println("autok_listazasa");
					}
				} break;
				
				case AT:
				case AUTO_TORLESE: {
					if(command_args.length == 2){
						if(admin_user){
							int deleted_cars = car_controller.DeleteCar(command_args[1]);
							if(deleted_cars > 0){
								System.out.println("Sikeres torles!");
							}else{
								System.out.println("Nincs ilyen rendszamu auto a rendszerben.");
							}
						}else if(user != null){
							if(user.GetLicensePlate().equals(command_args[1])){
								int deleted_cars = car_controller.DeleteCar(command_args[1]);
								if(deleted_cars > 0){
									System.out.println("Sikeres torles!");
								}else{
									System.out.println("Nincs ilyen rendszamu auto a rendszerben.");
								}
								user = null;
							}else{
								System.out.println("Ha nem adminisztratorkent van bejelentkezve, csak a sajat autojat tudja torolni.");
								System.out.println("Ha be szeretne jelentkezni adminisztratorkent, hasznalja a 'bejelentkezes' parancsot!");
							}
						}else{
							System.out.println("Auto torlesehez be kell jelentkezni.");
							System.out.println("Ha be szeretne jelentkezni, hasznalja a 'bejelentkezes' parancsot!");
						}
					}else if(command_args.length == 1){
						if(user != null){
							int deleted_cars = car_controller.DeleteCar(user.GetLicensePlate());
							if(deleted_cars > 0){
								System.out.println("Sikeres torles!");
							}else{
								System.out.println("A bejelentkezett felhasznalohoz tartozo auto mar nincs a rendszerben.");
							}
							user = null;
						}else if(admin_user){
							System.out.println("Az 'autok_torlese' parancsnak nem 0 parametere van.");
							System.out.println("A parancs helyes hasznalata adminisztratorkent:");
							System.out.println("auto_torlese");
						}else{
							System.out.println("Auto torlesehez be kell jelentkezni.");
							System.out.println("Ha be szeretne jelentkezni, hasznalja a 'bejelentkezes' parancsot!");
						}
					}else{
						System.out.println("Az 'autok_torlese' parancsnak nem "+(command_args.length-1)+" parametere van.");
						System.out.println("A parancs helyes hasznalata adminisztratorkent:");
						System.out.println("auto_torlese rendszam");
						System.out.println("A parancs helyes hasznalata nem adminisztratorkent:");
						System.out.println("auto_torlese");
					}
				} break;
				
				case UA:
				case UJ_AUTO: {
					if(command_args.length == 4){
						String license_plate = command_args[1];
						boolean handicapped  = false;
						boolean electric     = false;
						boolean correct_data = true;
						
						if(command_args[2].toUpperCase().equals("I")){
							handicapped = true;
						}else if(command_args[2].toUpperCase().equals("N")){
							handicapped = false;
						}else{
							System.out.println("A "+command_args[2]+" helytelen ertek a mozgaskorlatozottsag megadasahoz.");
							System.out.println("Helyes ertekek: i, n.");
							correct_data = false;
						}
						
						if(command_args[3].toUpperCase().equals("I")){
							electric = true;
						}else if(command_args[3].toUpperCase().equals("N")){
							electric = false;
						}else{
							System.out.println("A "+command_args[3]+" helytelen ertek az elektromossag megadasahoz.");
							System.out.println("Helyes ertekek: i, n.");
							correct_data = false;
						}
						
						if(correct_data){
							Car new_car = car_controller.AddCar(license_plate, handicapped, electric);
							if(new_car != null){
								if(!admin_user){
									user = new_car;
								}
								System.out.println("Uj auto sikeresen letrehozva!");
							}
						}
						
					}else{
						System.out.println("Az 'uj_auto' parancsnak nem "+(command_args.length-1)+" parametere van.");
						System.out.println("A parancs helyes hasznalata:");
						System.out.println("uj_auto rendszam mosgaskorlatozott(i/n) elektromos(i/n)");
					}
				} break;
				
				case F:
				case FOGLALAS: {
					if(command_args.length == 8){
						if(admin_user || user != null){
							int column               = 0;
							int row                  = 0;
							LocalDateTime start_time = null;
							LocalDateTime end_time   = null;
							
							boolean correct_data = true;
							
							try{
								column = Integer.parseInt(command_args[1]);
							}catch(Exception e){
								System.out.println("'"+command_args[1]+"' nem szam.");
								correct_data = false;
							}
							
							try{
								row = Integer.parseInt(command_args[2]);
							}catch(Exception e){
								System.out.println("'"+command_args[2]+"' nem szam.");
								correct_data = false;
							}
							
							ParkingSpace parking_space = null;
							if(correct_data){
								parking_space = ParkingSpaceView.GetParkingSpaceByColumnAndRow(column-1, row-1);
								if(parking_space == null){
									System.out.println("Nincs a megadott oszlopnak es sornak megfelelo parkolohely.");
									correct_data = false;
								}
							}
							
							try{
								start_time = LocalDateTime.parse(command_args[3]+" "+command_args[4], formatter);
							}catch(Exception e){
								System.out.println("'"+command_args[3]+" "+command_args[4]+"' helytelen datum.");
								System.out.println("A helyes datum formatum: YYYY-MM-dd HH:mm:ss");
								correct_data = false;
							}
							
							try{
								end_time = LocalDateTime.parse(command_args[5]+" "+command_args[6], formatter);
							}catch(Exception e){
								System.out.println("'"+command_args[5]+" "+command_args[6]+"' helytelen datum.");
								System.out.println("A helyes datum formatum: YYYY-MM-dd HH:mm:ss");
								correct_data = false;
							}
							
							
							if(end_time != null && start_time != null){
								if(start_time.compareTo(end_time) > 0){
									System.out.println("Az idosav_kezdete korabbi idopont kell hogy legyen, mint az idosav_vege.");
									correct_data = false;
								}
							}
							
							
							Car car = car_controller.GetCarByLicensePlate(command_args[7]);
							if(car == null){
								System.out.println("Nincs ilyen rendszamu auto a rendszerben.");
								correct_data = false;
							}else if(user != null && car.GetId() != user.GetId()){
								System.out.println("Nem adminisztrator felhasznalo csak a sajat autojahoz igenyelhet foglalast.");
								correct_data = false;
							}
							
							if(correct_data){
								List<Reservation> reservations_at_space = reservation_controller.ListReservationsByTimePeriodAndSpace(start_time, end_time, parking_space);
								boolean reserved_by_different_car = false;
								for(Reservation reservation : reservations_at_space){
									if(!reservation.GetReservator().GetLicensePlate().equals(car.GetLicensePlate())){
										reserved_by_different_car = true;
										break;
									}
								}
								
								if(reserved_by_different_car){
									System.out.println("Ezt a parkolohelyet mar lefoglaltak az adott idosavban.");
								}else{
									List<Reservation> reservations_by_car = reservation_controller.ListReservationsByTimePeriodAndCar(start_time, end_time, car);
									boolean can_make_reservation = reservations_by_car.size() == 0;
									if(!can_make_reservation){
										System.out.println("Ennek az autonak mar van foglalasa az adott idosavban:");
										ReservationView.DisplayReservations(reservations_by_car);
										
										System.out.println("Szeretne ezeket torolni es letrehozni az uj foglalast? (i/n)");
										
										String cur_command = scanner.nextLine().strip().toUpperCase();
										can_make_reservation = cur_command.equals("I");
										
										if(can_make_reservation){
											for(Reservation reservation : reservations_by_car){
												reservation_controller.DeleteReservation(reservation.GetId());
											}
										}
									}
									
									if(can_make_reservation){
										
										if(parking_space.GetType() == ParkingSpace.ParkingSpaceType.HANDICAPPED && !car.GetCanUseHandicappedSpace()){
											System.out.println("Ez az auto nem parkolhat mozgaskorlatozott parkolohelyre.");
											can_make_reservation = false;
										}
										
										if(parking_space.GetType() == ParkingSpace.ParkingSpaceType.ELECTRIC && !car.GetCanUseElectricSpace()){
											System.out.println("Ez az auto nem parkolhat elektromos parkolohelyre.");
											can_make_reservation = false;
										}
										
										if(can_make_reservation){
											reservation_controller.AddReservation(start_time, end_time, car, parking_space);
											
											System.out.println("Foglalas sikeresen letrehozva!");
										}
									}
								}
							}else{
								System.out.println("A parancs helyes hasznalata adminisztratorkent:");
								System.out.println("foglalas oszlop sor idosav_kezdete idosav_vege rendszam");
								System.out.println("A parancs helyes hasznalata nem adminisztratorkent:");
								System.out.println("foglalas oszlop sor idosav_kezdete idosav_vege");
							}
							
						}else{
							System.out.println("A 'foglalas' parancs hasznalatahoz be kell jelentkezni.");
						}
					}else if(command_args.length == 7){
						if(admin_user){
							System.out.println("A 'foglalas' parancs hasznalatanal adminisztratorkent meg kell adni a foglalo rendszamat!");
							System.out.println("A parancs helyes hasznalata adminisztratorkent:");
							System.out.println("foglalas oszlop sor idosav_kezdete idosav_vege rendszam");
						
						}else if(user != null){
							
							int column               = 0;
							int row                  = 0;
							LocalDateTime start_time = null;
							LocalDateTime end_time   = null;
							
							boolean correct_data = true;
							
							try{
								column = Integer.parseInt(command_args[1]);
							}catch(Exception e){
								System.out.println("'"+command_args[1]+"' nem szam.");
								correct_data = false;
							}
							
							try{
								row = Integer.parseInt(command_args[2]);
							}catch(Exception e){
								System.out.println("'"+command_args[2]+"' nem szam.");
								correct_data = false;
							}
							
							ParkingSpace parking_space = null;
							if(correct_data){
								parking_space = ParkingSpaceView.GetParkingSpaceByColumnAndRow(column-1, row-1);
								if(parking_space == null){
									System.out.println("Nincs a megadott oszlopnak es sornak megfelelo parkolohely.");
									correct_data = false;
								}
							}
							
							try{
								start_time = LocalDateTime.parse(command_args[3]+" "+command_args[4], formatter);
							}catch(Exception e){
								System.out.println("'"+command_args[3]+" "+command_args[4]+"' helytelen datum.");
								System.out.println("A helyes datum formatum: YYYY-MM-dd HH:mm:ss");
								correct_data = false;
							}
							
							try{
								end_time = LocalDateTime.parse(command_args[5]+" "+command_args[6], formatter);
							}catch(Exception e){
								System.out.println("'"+command_args[5]+" "+command_args[6]+"' helytelen datum.");
								System.out.println("A helyes datum formatum: YYYY-MM-dd HH:mm:ss");
								correct_data = false;
							}
							
							
							if(end_time != null && start_time != null){
								if(start_time.compareTo(end_time) > 0){
									System.out.println("Az idosav_kezdete korabbi idopont kell hogy legyen, mint az idosav_vege.");
									correct_data = false;
								}
							}
							
							
							if(correct_data){
								List<Reservation> reservations_at_space = reservation_controller.ListReservationsByTimePeriodAndSpace(start_time, end_time, parking_space);
								boolean reserved_by_different_car = false;
								for(Reservation reservation : reservations_at_space){
									if(!reservation.GetReservator().GetLicensePlate().equals(user.GetLicensePlate())){
										reserved_by_different_car = true;
										break;
									}
								}
								
								if(reserved_by_different_car){
									System.out.println("Ezt a parkolohelyet mar lefoglaltak az adott idosavban.");
								}else{
									List<Reservation> reservations_by_car = reservation_controller.ListReservationsByTimePeriodAndCar(start_time, end_time, user);
									boolean can_make_reservation = reservations_by_car.size() == 0;
									if(!can_make_reservation){
										System.out.println("Ennek az autonak mar van foglalasa az adott idosavban:");
										ReservationView.DisplayReservations(reservations_by_car);
										
										System.out.println("Szeretne ezeket torolni es letrehozni az uj foglalast? (i/n)");
										
										String cur_command = scanner.nextLine().strip().toUpperCase();
										can_make_reservation = cur_command.equals("I");
										
										if(can_make_reservation){
											for(Reservation reservation : reservations_by_car){
												reservation_controller.DeleteReservation(reservation.GetId());
											}
										}
									}
									
									if(can_make_reservation){
										reservation_controller.AddReservation(start_time, end_time, user, parking_space);
										
										System.out.println("Foglalas sikeresen letrehozva!");
									}
								}
							}else{
								System.out.println("A parancs helyes hasznalata adminisztratorkent:");
								System.out.println("foglalas oszlop sor idosav_kezdete idosav_vege rendszam");
								System.out.println("A parancs helyes hasznalata nem adminisztratorkent:");
								System.out.println("foglalas oszlop sor idosav_kezdete idosav_vege");
							}
							
							
						}else{
							System.out.println("A 'foglalas' parancs hasznalatahoz be kell jelentkezni.");
						}
					}else{
						System.out.println("A 'foglalas' parancsnak nem "+(command_args.length-1)+" parametere van.");
						System.out.println("A parancs helyes hasznalata adminisztratorkent:");
						System.out.println("foglalas oszlop sor idosav_kezdete idosav_vege rendszam");
						System.out.println("A parancs helyes hasznalata nem adminisztratorkent:");
						System.out.println("foglalas oszlop sor idosav_kezdete idosav_vege");
					}
				} break;
				
				case FL:
				case FOGLALASOK_LISTAZASA: {
					if(command_args.length == 1){
						ReservationView.DisplayReservations(reservation_controller.ListReservations());
					}else{
						System.out.println("A 'foglalasok_listazasa' parancsnak nem "+(command_args.length-1)+" parametere van.");
						System.out.println("A parancs helyes hasznalata:");
						System.out.println("foglalasok_listazasa");
					}
				} break;
				
				case FT:
				case FOGLALAS_TORLESE: {
					if(command_args.length == 6){
						if(user != null || admin_user){
							LocalDateTime start_time = null;
							LocalDateTime end_time   = null;
							
							boolean correct_data = true;
							
							try{
								start_time = LocalDateTime.parse(command_args[1]+" "+command_args[2], formatter);
							}catch(Exception e){
								System.out.println("'"+command_args[1]+" "+command_args[2]+"' helytelen datum.");
								System.out.println("A helyes datum formatum: YYYY-MM-dd HH:mm:ss");
								correct_data = false;
							}
							
							try{
								end_time = LocalDateTime.parse(command_args[3]+" "+command_args[4], formatter);
							}catch(Exception e){
								System.out.println("'"+command_args[3]+" "+command_args[4]+"' helytelen datum.");
								System.out.println("A helyes datum formatum: YYYY-MM-dd HH:mm:ss");
								correct_data = false;
							}
							
							
							if(end_time != null && start_time != null){
								if(start_time.compareTo(end_time) > 0){
									System.out.println("Az idosav_kezdete korabbi idopont kell hogy legyen, mint az idosav_vege.");
									correct_data = false;
								}
							}
							
							
							Car car = car_controller.GetCarByLicensePlate(command_args[5]);
							if(car == null){
								System.out.println("Nincs ilyen rendszamu auto a rendszerben.");
								correct_data = false;
							}else if(user != null && car.GetId() != user.GetId()){
								System.out.println("Nem adminisztrator felhasznalo csak a sajat foglalasat torolheti.");
								correct_data = false;
							}
							
							if(correct_data){
								List<Reservation> reservations_by_car = reservation_controller.ListReservationsByTimePeriodAndCar(start_time, end_time, car);
								for(Reservation reservation : reservations_by_car){
									reservation_controller.DeleteReservation(reservation.GetId());
								}
								
								if(reservations_by_car.size() > 0){
									System.out.println("Foglalas sikeresen torolve!");
								}else{
									System.out.println("Nincs ilyen foglalas a rendszerben.");
								}
								
							}else{
								System.out.println("A parancs helyes hasznalata adminisztratorkent:");
								System.out.println("foglalas_torlese idosav_kezdete idosav_vege rendszam");
								System.out.println("A parancs helyes hasznalata nem adminisztratorkent:");
								System.out.println("foglalas_torlese idosav_kezdete idosav_vege");
							}
						}else{
							System.out.println("A 'foglalas_torlese' parancs hasznalatahoz be kell jelentkezni.");
						}
						
					}else if(command_args.length == 5){
						if(admin_user){
							System.out.println("A 'foglalas_torlese' parancs hasznalatanal adminisztratorkent meg kell adni a foglalo rendszamat!");
							System.out.println("A parancs helyes hasznalata adminisztratorkent:");
							System.out.println("foglalas_torlese idosav_kezdete idosav_vege rendszam");
							
						}else if(user != null){
							LocalDateTime start_time = null;
							LocalDateTime end_time   = null;
							
							boolean correct_data = true;
							
							try{
								start_time = LocalDateTime.parse(command_args[1]+" "+command_args[2], formatter);
							}catch(Exception e){
								System.out.println("'"+command_args[1]+" "+command_args[2]+"' helytelen datum.");
								System.out.println("A helyes datum formatum: YYYY-MM-dd HH:mm:ss");
								correct_data = false;
							}
							
							try{
								end_time = LocalDateTime.parse(command_args[3]+" "+command_args[4], formatter);
							}catch(Exception e){
								System.out.println("'"+command_args[3]+" "+command_args[4]+"' helytelen datum.");
								System.out.println("A helyes datum formatum: YYYY-MM-dd HH:mm:ss");
								correct_data = false;
							}
							
							
							if(end_time != null && start_time != null){
								if(start_time.compareTo(end_time) > 0){
									System.out.println("Az idosav_kezdete korabbi idopont kell hogy legyen, mint az idosav_vege.");
									correct_data = false;
								}
							}
							
							if(correct_data){
								List<Reservation> reservations_by_car = reservation_controller.ListReservationsByTimePeriodAndCar(start_time, end_time, user);
								for(Reservation reservation : reservations_by_car){
									reservation_controller.DeleteReservation(reservation.GetId());
								}
								
								if(reservations_by_car.size() > 0){
									System.out.println("Foglalas sikeresen torolve!");
								}else{
									System.out.println("Nincs ilyen foglalas a rendszerben.");
								}
								
							}else{
								System.out.println("A parancs helyes hasznalata adminisztratorkent:");
								System.out.println("foglalas_torlese idosav_kezdete idosav_vege rendszam");
								System.out.println("A parancs helyes hasznalata nem adminisztratorkent:");
								System.out.println("foglalas_torlese idosav_kezdete idosav_vege");
							}
						}else{
							System.out.println("A 'foglalas_torlese' parancs hasznalatahoz be kell jelentkezni.");
						}
					}else{
						System.out.println("A 'foglalas_torlese' parancsnak nem "+(command_args.length-1)+" parametere van.");
						System.out.println("A parancs helyes hasznalata adminisztratorkent:");
						System.out.println("foglalas_torlese idosav_kezdete idosav_vege rendszam");
						System.out.println("A parancs helyes hasznalata nem adminisztratorkent:");
						System.out.println("foglalas_torlese idosav_kezdete idosav_vege");
					}
				} break;
				
				case SUGO: {
					System.out.println("Hasznalhato parancsok:");
					System.out.println("");
					System.out.println("bejelentkezes / b rendszam|admin");
					System.out.println("    Bejelentkezes az adott rendszamu autoval vagy adminisztratorkent.");
					System.out.println("");
					System.out.println("kijelentkezes / k");
					System.out.println("    Kijelentkezes a hasznalt fiokbol.");
					System.out.println("");
					System.out.println("elrendezes_modositasa / em oszlopok_szama");
					System.out.println("    A parkolohelyek elrendezesenek modositasa a racs oszlopszamanak megadasaval. Csak adminisztratorkent hasznalhato.");
					System.out.println("");
					System.out.println("parkolohelyek_modositasa / phm egyszeru_helyek_szama mozgaskorlatozott_helyek_szama elektromos_helyek_szama");
					System.out.println("    A parkolohelyek szamanak modositasa a kulonbozo tipusu helyek szamanak megadasaval. Csak adminisztratorkent hasznalhato.");
					System.out.println("");
					System.out.println("parkolohelyek_listazasa / phl");
					System.out.println("    Megjeleniti a parkolohelyeket egy racsban.");
					System.out.println("");
					System.out.println("autok_listazasa / al");
					System.out.println("    Megjeleniti a regisztralt autok adatait. Csak bejelentkezett felhasznalok hasznalhatjak. Egyszeru felhasznalokent csak a sajat auto adatai jelennek meg, adminisztratorkent az osszes auto adatai.");
					System.out.println("");
					System.out.println("auto_torlese / at [rendszam]");
					System.out.println("    Torol egy regisztralt autot. Csak bejelentkezett felhasznalok hasznalhatjak. Egyszeru felhasznalokent csak a sajat auto torolheto, adminisztatorkent barmely regisztralt auto. Adminisztratorkent meg kell adni a torlendo auto rendszamat.");
					System.out.println("");
					System.out.println("uj_auto / ua rendszam mosgaskorlatozott(i/n) elektromos(i/n)");
					System.out.println("    Uj autot regisztral a rendszerbe a megadott adatokkal.");
					System.out.println("");
					System.out.println("foglalasok_listazasa / fl");
					System.out.println("    Megjeleniti az eddigi foglalasok adatait.");
					System.out.println("");
					System.out.println("foglalas / f oszlop sor idosav_kezdete idosav_vege [rendszam]");
					System.out.println("    Uj foglalast hoz letre a rendszerben az adott oszlopban es sorban levo parkolohelyre, a megadott adatokkal. Csak bejelentkezett felhasznalok hasznalhatjak. Egyszeru felhasznalokent csak a sajat autonak lehet foglalni, adminisztratorkent barmelyik autonak a megadott rendszam alapjan. Ha az adott autonak mar van foglalasa az adott idosavban, a rendszer felkinalja a lehetoseget a meglevo foglalas torlesere es az uj letrehozasara.");
					System.out.println("");
					System.out.println("foglalas_torlese / ft idosav_kezdete idosav_vege [rendszam]");
					System.out.println("    Torli az adott idosavban ervenyes, adott autohoz tartozo foglalasokat. Csak bejelentkezett felhasznalok hasznalhatjak. Egyszeru felhasznalokent csak a sajat foglalasokat lehet torolni, adminisztratorkent barmelyik auto foglalasat, a megadott rendszam alapjan.");
					System.out.println("");
					System.out.println("sugo");
					System.out.println("    Megjeleniti a hasznalhato parancsokat.");
					System.out.println("");
					System.out.println("kilepes");
					System.out.println("    Leallitja a rendszert.");
					System.out.println("");
				} break;
				
				default: {
					System.out.println("'"+command_args[0].toLowerCase()+"' nem felismerheto parancs. Hasznalja a 'sugo' parancsot a lehetseges parancsok megjelenitesehez!");
				} break;
			}
			
			command = scanner.nextLine().strip();
			command_args = command.split(" ");
			command_args[0] = command_args[0].toUpperCase();
		}
		
	}
	
	
}


