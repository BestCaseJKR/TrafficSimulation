package model.Vehicle;

import model.Agent.TimeServer;
import model.VehicleAcceptor.VehicleAcceptor;
/**
 * Basic factory class for objects implementing the vehicle interface
 * @author johnreagan
 *
 */
public class VehicleFactory {
	VehicleFactory () { }
	/**
	 * Return a new VehicleAcceptor(Car)
	 * @param ts
	 * @param va
	 * @return
	 */
	public static Vehicle newCar(TimeServer ts, VehicleAcceptor va) {
		
		Car c = new Car(ts, va);
		return c;
		
	}
	
}
