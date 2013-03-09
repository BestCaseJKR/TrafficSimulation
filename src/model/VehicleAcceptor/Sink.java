package model.VehicleAcceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import model.MP;
import model.Agent.Agent;
import model.Agent.TimeServer;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleOrientation;

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
		List<Vehicle> lc = new ArrayList<Vehicle>(_cars);
		for(Vehicle c: lc) {
			remove(c);
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
		//System.out.println("Car reached sink");
		_cars.add(d);
	    d.setDisposed();
		return true;
		
		
	}

	@Override
	public void remove(Vehicle d) {
		_cars.remove(d);
		//System.out.println("Removed " + d + " from " + this);
		
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
		return Double.MAX_VALUE;
	}

}
