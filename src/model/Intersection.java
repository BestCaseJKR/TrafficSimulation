package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Intersection implements VehicleAcceptor {
	
	Intersection() { System.out.println(this);  }
	
	private VehicleAcceptor _nsRoad;
	public VehicleAcceptor getNSRoad() {
		return _nsRoad;
	}
	public void setNSRoad(VehicleAcceptor r) {
		_nsRoad = r;
	}
	
	private VehicleAcceptor _ewRoad;
	
	public VehicleAcceptor getEWRoad() {
		return _ewRoad;
	}
	public void setEWRoad(VehicleAcceptor r) {
		_ewRoad = r;
	}
	
	private double _length = MP.intersectionLength.getDoubleInRange();
	
	private Light _light;
	public void setLight(Light l) {
		_light = l;
	}
	public Light getLight() {
		return _light;
	}

	private Queue<Vehicle> _cars = new LinkedList<Vehicle>();
	
	@Override
	public boolean isDriveable(Vehicle c) {
		return c.getOrientation().equals(_light.getState().getAllowedOrientation());
	}
	@Override
	public boolean accept(Vehicle d) {
		// TODO Check light and decide to accept
	    if (d == null) { throw new IllegalArgumentException(); }
	    if (!isDriveable(d))  {
	    	return false;
	    }
	    System.out.println("Intersection has car " + d);
	    _cars.add(d);
	    return true;
	}
	@Override
	public void remove(Vehicle d) {
		_cars.remove(d);
	}
	
	  public void setNextSeg(VehicleAcceptor next) {
		  
	  }
	  public VehicleAcceptor getNextSeg(Vehicle c) { 
		  if (c.getOrientation() == VehicleOrientation.East_West) {
			  return this.getEWRoad();
		  } else {
			  return this.getNSRoad();
		  }
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
	public Queue<Vehicle> getCars() {
		return _cars;
	}
	@Override
	public double getLength() {
		return _length;
	}

	public String toString() {
		return "Intersection: L: " + this.getLength() + " with cars " + this.getCars();
	}
	
}
