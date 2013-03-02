package model;

import java.util.LinkedList;
import java.util.Queue;


public class Road implements VehicleAcceptor {

	/**
	 * a list of vehicle objects currently "on" this road
	 */
	private Queue<Vehicle> _cars = new LinkedList<Vehicle>();
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
	
	public boolean isDriveable(Vehicle c) {
		return true;
	}
	
	public boolean accept(Vehicle d) {
	    if (d == null) { throw new IllegalArgumentException(); }
	    if (!isDriveable(d)) return false;
	    _cars.add(d);
	    return true;
	}

	@Override
	public void remove(Vehicle d) {
	    if (d != null) {
	    	_cars.remove(d);
	    }
	}

	@Override
	public void setNextSeg(VehicleAcceptor next) {
		if (next == null) throw new IllegalArgumentException(); 
		_next = next;
	}

	@Override
	public VehicleAcceptor getNextSeg(Vehicle c) {
		return _next;
	}

	@Override
	public void setOrientation(VehicleOrientation o) {
		_orientation = o;
	}

	@Override
	public VehicleOrientation getOrientation() {
		return _orientation;
	}

	@Override
	public Queue<Vehicle> getCars() {
		return _cars;
	}

	@Override
	public double getLength() {
		return _length;
	}

}
