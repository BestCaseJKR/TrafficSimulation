package model.VehicleAcceptor;

import java.util.Queue;
import java.util.SortedSet;

import model.Vehicle.Vehicle;
import model.Vehicle.VehicleOrientation;
/**
 * Interface describing an object capable of accepting Vehicle objects.
 * Very much like a road. Responsible for reporting if it is currently accepting
 * Vehicles, accepting vehicles(when possible of course), removing vehicles, 
 * setting and getting the next VehicleAcceptor and returning a list of the current
 * cars which are in this road. 
 *
 */
public interface VehicleAcceptor {
	/**
	 * Test if the VehicleAcceptor is prepared to accept the Vehicle object
	 * @param c
	 * @return
	 */
	public boolean isDriveable(Vehicle c);
	/**
	 * add a car to the list.
	 * @param d
	 * @return
	 */
	public boolean accept(Vehicle d);
	/**
	 * remove the Vehicle from the List
	 * @param d
	 */
	public void remove(Vehicle d);
	/**
	 * set the next VehicleAcceptor object. Think the next segment of road.	
	 * @param next
	 */
	public void setNextSeg(VehicleAcceptor next);
	/**
	 * get the next VehicleAcceptor object
	 * @param c 
	 * @return
	 * TODO: Improve interface to not rely on a Vehicle to return its next segment
	 */
	public VehicleAcceptor getNextSeg(Vehicle c);
	/**
	 * set the VehicleOrientation of the current road
	 * @param o
	 */
	public void setOrientation(VehicleOrientation o);
	/**
	 * get the VehicleOrientation of the road. 
	 * @return
	 */
	public VehicleOrientation getOrientation();
	/**
	 * return a COPY of the cars currently in the VehicleAcceptor
	 * @return
	 */
	public SortedSet<Vehicle> getCars();
	/**
	 * return the length of the road
	 * @return
	 */
	public double getLength();
	
}
