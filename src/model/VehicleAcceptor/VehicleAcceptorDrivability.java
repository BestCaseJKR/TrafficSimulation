package model.VehicleAcceptor;

import model.Vehicle.Vehicle;
/**
 * Drivability object which is used to represent different levels of drivability
 * @author johnreagan
 *
 */
public interface VehicleAcceptorDrivability {
	/**
	 * Check a VehicleAcceptor
	 * @return
	 */
	public boolean isDriveable(Vehicle v);
	
}
