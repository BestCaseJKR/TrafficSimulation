package model;

public class VehicleFactory {
	VehicleFactory () { }
	
	public static Vehicle newCar() {
		
		Car c = new Car(null, null);
		return c;
		
	}
	
}
