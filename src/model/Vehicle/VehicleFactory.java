package model.Vehicle;

import model.Agent.TimeServer;
import model.VehicleAcceptor.VehicleAcceptor;

public class VehicleFactory {
	VehicleFactory () { }
	
	public static Vehicle newCar(TimeServer ts, VehicleAcceptor va) {
		
		Car c = new Car(ts, va);
		return c;
		
	}
	
}
