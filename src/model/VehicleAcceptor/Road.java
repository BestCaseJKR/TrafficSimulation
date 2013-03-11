package model.VehicleAcceptor;

import model.MP;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleOrientation;

/**
 * Road class which implements VehicleAcceptor. Essentially a normal
 * road for the purposes of this simulation
 * @author johnreagan
 *
 */
public class Road extends VehicleAcceptor {


	/**
	 * the next vehicle acceptor in the graph
	 */
	private VehicleAcceptor _next;
	/**
	 * the Orientation of the vehicles on this road
	 */
	private VehicleOrientation _orientation;
	/**
	 * get the length of the road
	 */
	private double _length = MP.roadSegmentLength.getDoubleInRange();
	/**
	 * Return the drivabaility of the road.
	 */
	public Drivability isDriveable(Vehicle c) {
		return Drivability.Driveable;
	}
	/**
	 * set the next vehicleAcceptor next.
	 * @throws IllegalArgumentException if null value supplied
	 */
	public void setNextSeg(VehicleAcceptor next) {
		if (next == null) throw new IllegalArgumentException(); 
		_next = next;
	}

	/**
	 * Get the next segment
	 */
	public VehicleAcceptor getNextSeg(Vehicle c) {
		return _next;
	}

	/**
	 * Set the orientation of the road
	 */
	public void setOrientation(VehicleOrientation o) {
		_orientation = o;
	}

	/**
	 * get the orientation of the road
	 */
	public VehicleOrientation getOrientation() {
		return _orientation;
	}

	/**
	 * Return the length of the road
	 */
	public double getLength() {
		return _length;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
			for (Vehicle v: this.getCars()) {
				sb.append(v.toString());
			}
		
		return "Road(" + this.hashCode() + "): " + sb.toString();
		
	}

}
