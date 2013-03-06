package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

public class Intersection implements VehicleAcceptor {
	
	Intersection() { System.out.println(this);  }
	
	public String key;
	
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

	private SortedSet<Vehicle> _cars = new TreeSet<Vehicle>();
	
	@Override
	public boolean isDriveable(Vehicle c) {
		if (c.getOrientation()== _light.getState().getAllowedOrientation()) {
			System.out.println("Pass b/c " + _light.getState().getAllowedOrientation() + " != " + c.getOrientation());
		} else {
			System.out.println("Rejected b/c " + _light.getState().getAllowedOrientation() + " == " + c.getOrientation());			
		}
		return c.getOrientation()==_light.getState().getAllowedOrientation();
	}
	@Override
	public boolean accept(Vehicle d) {
		// TODO Check light and decide to accept
	    if (d == null) { throw new IllegalArgumentException(); }
	    if (!isDriveable(d))  {
	    	return false;
	    }
	    //System.out.println("Intersection has car " + d);

	    _cars.add(d);
	    return true;
	}
	@Override
	public void remove(Vehicle d) {
		//if cars isnt empty...
		if (!_cars.remove(d)) {
			System.out.println("Couldnt Remove " + d + " from " + this);
		}
		//System.out.println("Removed " + d + " from " + this);
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
	public SortedSet<Vehicle> getCars() {
		return _cars;
	}
	@Override
	public double getLength() {
		return _length;
	}

	public String toString() {
		return "Intersection(Key=" + this.key + "): L: " + this.getLength() + " with cars " + this.getCars();
	}
	
}
