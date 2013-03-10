package model.VehicleAcceptor;

import java.util.SortedSet;
import java.util.TreeSet;

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
public abstract class VehicleAcceptor {
	
	/**
	 * a list of vehicle objects currently "on" this road
	 */
	private SortedSet<Vehicle> _cars = new TreeSet<Vehicle>();
	
	/**
	 * Test if the VehicleAcceptor is prepared to accept the Vehicle object
	 * @param c
	 * @return
	 */
	public abstract Drivability isDriveable(Vehicle c);
	/**
	 * add a car to the list.
	 * @param d
	 * @return
	 */
	public boolean accept(Vehicle d) {
	    if (d == null) { throw new IllegalArgumentException(); }
	    if (isDriveable(d) == Drivability.NotDriveable) return false;
	    _cars.add(d);
	    return true;
	}
	/**
	 * remove the Vehicle from the List
	 * @param d
	 */
	public void remove(Vehicle d) {
	    if (d != null) {
	    	_cars.remove(d);
	    	//System.out.println("Removed " + d + " from " + this);
	    }
	}
	/**
	 * return a COPY of the cars currently in the VehicleAcceptor
	 * @return
	 */
	public SortedSet<Vehicle> getCars() {
		return _cars;
	}
	/**
	 * set the next VehicleAcceptor object. Think the next segment of road.	
	 * @param next
	 */
	public abstract void setNextSeg(VehicleAcceptor next);
	/**
	 * get the next VehicleAcceptor object
	 * @param c 
	 * @return
	 * TODO: Improve interface to not rely on a Vehicle to return its next segment
	 */
	public abstract VehicleAcceptor getNextSeg(Vehicle c);
	/**
	 * set the VehicleOrientation of the current road
	 * @param o
	 */
	public abstract void setOrientation(VehicleOrientation o);
	/**
	 * get the VehicleOrientation of the road. 
	 * @return
	 */
	public abstract VehicleOrientation getOrientation();
	/**
	 * return the length of the road
	 * @return
	 */
	public abstract double getLength();
	
}
