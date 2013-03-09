package model.Vehicle;

import model.Disposable;
import model.VehicleAcceptor.VehicleAcceptor;

/**
 * interface for a vehicle object.
 * Vehicles should be able to maintain their position, Orientation, length(and back position)
 * They are also responsible for moving themselves to a new VehicleAcceptor, checking
 * to see if there is space for itself on a VehicleAcceptor
 */
public interface Vehicle extends Disposable {
	/**
	 * Return the Vehicle's current VehicleAcceptor object
	 * @return the Vehicle's current VehicleAcceptor object
	 */
	public VehicleAcceptor getCurrentRoad();
	/**
	 * Return the position of the front of the vehicle
	 * @return the position of the front of the vehicle
	 */
	public double getPosition();
	/**
	 * Return the length of the Vehicle
	 * @return
	 */
	public double getLength();
	/**
	 * Get the braking distance of the current object
	 * @return
	 */
	public double getBrakeDistance();
	/**
	 * Return the position of the back of the vehicle, the bumper if you will.
	 * @return
	 */
	public double getBackPosition();
	/**
	 * return the Color of the Vehicle
	 * @return
	 */
	public java.awt.Color getColor();
	/**
	 * Move the car to the next segment of road.
	 * @param requestedPostion request the supplied position and move to it if possible
	 * @return
	 */
	public boolean sendCarToNextSeg(double requestedPostion);
	/**
	 * Return the orientation(VehicleOrientation enum) of the vehicle
	 * @return
	 */
	public VehicleOrientation getOrientation();
	/**
	 * request to move this vehicle on the current road
	 * @param requestedMove
	 * @return
	 */
	public void moveTo(double requestedMove);
	/**
	 * check the VehicleAcceptor object and see if it could currently accept the vehicle
	 * @param r
	 * @return
	 */
	public boolean isSpaceForCar(VehicleAcceptor r);
	/**
	 * Update the current VehicleAcceptor object and set an initial position
	 * @param _next
	 * @param length
	 */
	public void setCurrentVehicleAcceptor(VehicleAcceptor _next, double length);

}