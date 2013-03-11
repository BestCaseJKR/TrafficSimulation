package model.VehicleAcceptor;

import model.MP;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleOrientation;
/**
 * Class used to represent intersections in the traffic simulation.
 * This class implements VehicleAcceptor and acts very much like a road.
 * However, it has additional functionality in the form of a light controller
 * which allows it to deny access to cars from certain directions. It also
 * must provide different response to calls to getNextSeg() based on the orientation
 * of the requesting vehicle
 * @author johnreagan
 *
 */
public class Intersection extends VehicleAcceptor {
	//only created by this package
	Intersection() {  }
	/**
	 * The road which NS cars are sent to after leaving this intersection
	 */
	private VehicleAcceptor _nsRoad;
	/**
	 * Get the next road for North_South oriented vehicles
	 * @return
	 */
	public VehicleAcceptor getNSRoad() {
		return _nsRoad;
	}
	/**
	 * Set the next Road for North_South oriented vehicles
	 * @param r
	 */
	public void setNSRoad(VehicleAcceptor r) {
		_nsRoad = r;
	}
	/**
	 * The next road segment for EW oriented vehicles
	 */
	private VehicleAcceptor _ewRoad;
	/**
	 * Get the next road for East_West oriented vehicles
	 * @return
	 */	
	public VehicleAcceptor getEWRoad() {
		return _ewRoad;
	}
	/**
	 * Set the next Road for East_West oriented vehicles
	 * @param r
	 */	
	public void setEWRoad(VehicleAcceptor r) {
		_ewRoad = r;
	}
	/**
	 * Set the length of the intersection as a random double within
	 * the MP range. Each intersection will have a fixed, but potentially
	 * differnt length.
	 */
	private double _length = MP.intersectionLength.getDoubleInRange();
	/**
	 * The light control which houses and controls the lights and transitions
	 */
	private LightController _lightControl;
	/**
	 * Set the lightcontroller
	 * @param l
	 */
	public void setLightControl(LightController l) {
		_lightControl = l;
	}
	/**
	 * get the light controller
	 * @return
	 */
	public LightController getLightControl() {
		return _lightControl;
	}

	/**
	 * Rreturn the Drivability of the intersection for the Vehicle c.
	 * If the current allowed orientation matches, retunr Driveable or Caution,
	 * if not return NotDriveable to deny access i.e. a red light.
	 */
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

	/**
	 * Dummy method since this class does not implement it
	 */
	  public void setNextSeg(VehicleAcceptor next) {
		  
	  }
	  /**
	   * Check the vehicle's orientation and return the correct next segment.
	   * Ignores the current state of the light in case a car enters and then the light 
	   * transitions.
	   */
	  public VehicleAcceptor getNextSeg(Vehicle c) { 
		  if (c.getOrientation() == VehicleOrientation.East_West) {
			  return this.getEWRoad();
		  } else {
			  return this.getNSRoad();
		  }
	  }
	/**
	 * N/A
	 */
	public void setOrientation(VehicleOrientation o) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * N/A
	 */
	public VehicleOrientation getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Return length of intersection
	 */
	public double getLength() {
		return _length;
	}
	/**
	 * return string representing intersection
	 */
	public String toString() {
		return "Intersection: L: " + this.getLength() + " with cars " + this.getCars();
	}
	
}
