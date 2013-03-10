package model.VehicleAcceptor;

import model.MP;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleOrientation;

public class Intersection extends VehicleAcceptor {
	
	Intersection() {  }
	
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
	
	private LightController _lightControl;
	public void setLightControl(LightController l) {
		_lightControl = l;
	}
	public LightController getLightControl() {
		return _lightControl;
	}

	
	public Drivability isDriveable(Vehicle c) {
		if (c.getOrientation()== _lightControl.getState().getAllowedOrientation()) {
			if (_lightControl.getState().isYellow()) {
				return Drivability.Caution;
			}
			return Drivability.Driveable;
		} else {
			return Drivability.NotDriveable;
		}
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
	public double getLength() {
		return _length;
	}

	public String toString() {
		return "Intersection: L: " + this.getLength() + " with cars " + this.getCars();
	}
	
}
