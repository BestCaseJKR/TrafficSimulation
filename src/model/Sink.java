package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

public class Sink implements Agent, VehicleAcceptor {
	
	public Sink (TimeServer ts) {
		_ts = ts;
		_ts.enqueue(_ts.currentTime()+MP.simulationTimeStep, this);
	}
	/**
	 * the vehicles in the sink are stored here before being destroyed
	 */
	private SortedSet<Vehicle> _cars = new TreeSet<Vehicle>();
	/**
	 * The attached TimeServer object
	 */
	private TimeServer _ts;
	
	@Override
	public void run() {
		for(Vehicle c: _cars) {
			_cars.remove(c);
			c.setDisposed();
			c = null;
		}
		_ts.enqueue(_ts.currentTime() + MP.simulationTimeStep, this);
	}

	@Override
	public boolean isDriveable(Vehicle c) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setNextSeg(VehicleAcceptor next) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean accept(Vehicle d) {
		if(d == null) throw new IllegalArgumentException();
		System.out.println("Car reached sink");
		_cars.add(d);
		
		return true;
		
		
	}

	@Override
	public void remove(Vehicle d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Road getNextSeg(Vehicle c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrientation(VehicleOrientation o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public VehicleOrientation getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<Vehicle> getCars() {
		return _cars;
	}

	@Override
	public double getLength() {
		return 50;
	}

}
