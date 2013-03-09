package model.Vehicle;

import java.awt.Color;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.SortedSet;

import model.MP;
import model.Agent.Agent;
import model.Agent.TimeServer;
import model.VehicleAcceptor.Intersection;
import model.VehicleAcceptor.VehicleAcceptor;


public class Car implements Vehicle, Agent, Comparable<Vehicle> {
	
	Car(TimeServer ts, VehicleAcceptor r) {
		_ts = ts;
		this.setCurrentVehicleAcceptor(r, this.getLength());
		_orientation = r.getOrientation();
		_ts.enqueue(ts.currentTime()+MP.simulationTimeStep, this);
	}
	
	/**
	 * The attached TimeServer object
	 */
	private final TimeServer _ts;
	/**
	 * the current road the car is on
	 */
	private VehicleAcceptor _currentRoad;
	/**
	 * the length of the vehicle. The amount of space it takes on the road.
	 */
	private double _length = MP.carLength.getDoubleInRange();
	/**
	 * boolean indicating if the agent is disposed or not, default to false
	 */
	private boolean _isDisposed = false;
	/**
	 * the position of the car in the _currentRoad;
	 */
	private double _position = 0;
	/**
	 * how fast an object move per second
	 */
	private double _velocity = MP.maxVelocity.getDoubleInRange();
	private double _brakeDistance = MP.breakDistance.getDoubleInRange();
	private double _stopDistance = MP.stopDistance.getDoubleInRange();
	/**
	 * The orientation of the vehicle
	 */
	private VehicleOrientation _orientation;
	/**
	 * The color the car will appear as when displayed on the UI
	 */
	private java.awt.Color _color = new java.awt.Color((int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255));
	  /**
	   * Updates currentRoad reference and places car manually at the requested position.
	   * @param road
	   * @param requestedPostion
	   */
	  public void setCurrentVehicleAcceptor(VehicleAcceptor road, double requestedPostion) {
		  
		  _currentRoad = road;
//		  //reset the position
//		  this.setPosition(0);
//		  //this.moveTo(requestedPostion);
//		  double free = this.checkFreeSpaceAhead();
		  //if the road is clear AND we want to skip it, we need to pass the car along to the next road AGAIN
		  if (requestedPostion > road.getLength()) {
			  //System.out.println("SKIP ROAD");
			  if (sendCarToNextSeg(requestedPostion - road.getLength())) {
				  return;
			  } else {
				  //the send op failed, just move to the free space
				  this.setPosition(road.getLength());
			  }
		  } else {
			  //our request was shorter than the length and the free space, request granted
			  this.setPosition(requestedPostion);
		  }
		  
		  
		  //this.setPosition(requestedPostion);
		  System.out.println("Added car(" + this.hashCode() + ") to VA: Current Postion: " + _position + " Requested: " + requestedPostion + " velocity: " + _velocity  + " in Road: " + this.getCurrentRoad());
	  }
	
	  void setPosition(double position) {
		  if (position > this.getCurrentRoad().getLength() || position < 0) throw new IllegalArgumentException("Position " + position + " exceeds the road length " + this.getCurrentRoad().getLength());
		  
		  //remove the car from the road its in, update the position then add it back to ensure its sorted properly
		  this.getCurrentRoad().remove(this);
		  _position = position;
		  this.getCurrentRoad().accept(this);
	  }
  
	  	public double checkFreeSpaceAhead() {
	  		
			//get a copy of all the vehicles in the VehicleAccpetor object
			SortedSet<Vehicle> cars = this.getCurrentRoad().getCars();
			//if the set is empty, return the road length
			if (cars.size() == 0) {
				double d = getFreeSpaceInOutsideVehicleAcceptor(this.getCurrentRoad().getNextSeg(this));
				//System.out.println("Cars not found in road! " + this + " " + cars);
				return this.getCurrentRoad().getLength() + d;
			}
			//if the current car isn't in the set, return the back position of the last car
			//if (!cars.contains(this)) {
			//	System.out.println("Car(" + this.hashCode() + ") not in road(" + this.getCurrentRoad() + ") " + this + " return " + cars.last().getBackPosition());
			//	return cars.last().getBackPosition();
			//}
			
			if (this.equals(cars.first())) {
				//the car is first, return the remaining length of the road AND check the next roads for free space
				double d = getFreeSpaceInOutsideVehicleAcceptor(this.getCurrentRoad().getNextSeg(this));
				//System.out.println("FIRST CAR IN road(l=" + this.getCurrentRoad().getLength() + ") " + this + " return " + (this.getCurrentRoad().getLength() - this.getPosition()) + " " + d + " " + cars);
				return (this.getCurrentRoad().getLength() - this.getPosition()) + d;
			}
			//get a set of all elements which are greater than or equal to this object
			SortedSet<Vehicle> frontCars = cars.headSet(this);
			//System.out.println("Front Cars(" + frontCars.size() + ") in road(" + this.getCurrentRoad().hashCode() + " w/ length: " + this.getCurrentRoad().getLength() + "): " + frontCars + " vs " + cars + " " + this);
			if(frontCars.size() > 0 && !this.equals(frontCars.last())) {
				Vehicle v = frontCars.last();
				//System.out.println("V is next car " + v + " to " + this);
				//System.out.println("FCS: " + frontCars);
				return v.getBackPosition() - this.getPosition();
			} else {
				//System.out.println("ELSE " + (this.getCurrentRoad().getLength() - this.getPosition()) + " to " + this);
				return this.getCurrentRoad().getLength() - this.getPosition();
			}
	  		
	  	}
	  	/**
	  	 * calculate the open space in the provide VA
	  	 * @param va to check for open space
	  	 * @return
	  	 */
	  	private double getFreeSpaceInOutsideVehicleAcceptor(VehicleAcceptor va) {
	  		
	  		//if va is null, return 0
	  		if (va == null) {
	  			return 0;
	  		}
	  		try {
	  			//the va has cars, return the last backPos
	  			return va.getCars().last().getBackPosition();
	  		} catch(NoSuchElementException e) {
	  			//no cars were found, recursively call the function with the next road segment
	  			return va.getLength() + getFreeSpaceInOutsideVehicleAcceptor(va.getNextSeg(this));	
	  		}
	  	}
	  	
	  	/**
	  	 * Move the car to, at the most, the requested position
	  	 * @param toPosition
	  	 */
	  	public void moveTo(double toPosition) {
	  		//get the free space in front of us on this road
	  		double freeSpace = checkFreeSpaceAhead();

	  		
	  		//calculate velocity
	  		double velocity = (_velocity / (_brakeDistance - _stopDistance)) * (freeSpace - _stopDistance);
	  		//System.out.println("V1 = " + velocity);
	  		velocity = Math.max(0, velocity);
	  		//System.out.println("V2 = " + velocity);
	  		velocity = Math.min(_velocity, velocity);
	  		double step = (velocity * MP.simulationTimeStep);
	  		double nextPos = this.getPosition() + step;

	  		//if the road is clear AND the maxMove is beyond the current road road. try to send to the next road
	  		if (nextPos > this.getCurrentRoad().getLength())  {
	  			//send to the next segment and request the appropriate space.
	  			if (sendCarToNextSeg(nextPos - this.getCurrentRoad().getLength())) {
		    		  //System.out.println("Sent car to next seg " + this + " NP " + nextPos + " " + (nextPos - this.getCurrentRoad().getLength()));
		    		  //_ts.enqueue(MP.simulationTimeStep + _ts.currentTime(), this);
		    		  return;
		    	} else {
		    		//the car was rejected by the next road! set the max move to be the 
		    		//length of the road - stopDistance, which should be the max value for the position
		    		nextPos = this.getCurrentRoad().getLength() - _stopDistance;
		    	}
	  		}
	  		
	  		//System.out.println("C: " + this + " nextPos " + nextPos + " step " + Math.round(step) + " velocity = " + velocity + " fs: " + freeSpace + " rL = " + this.getCurrentRoad().getLength() + " VAL = " + (velocity * MP.simulationTimeStep));
	  		if (nextPos == this.getPosition()) {
	  			//System.out.println("Car stopped: " + this + " " + nextPos + " in road " + this.getCurrentRoad());
	  		}
	  		this.setPosition(nextPos);
	  		
	  	}
	@Override
	public void run() {
		//System.out.println(this + " is running! at " + _ts.currentTime());
		if (this.isDisposed()) {
			//object is disposed, don't do anything with it!
			return;
		}
			if (this.getCurrentRoad().getClass() == Intersection.class) {
				//System.out.println(this + " is inside the intersection! " + ((Intersection)this.getCurrentRoad()).key);
			}
		//call moveTo and pass the maximum possible move for this car	
		 this.moveTo(_velocity * MP.simulationTimeStep);
	    //System.out.println("(" + _ts.currentTime() + ")Running Car: " + this.toString() + " free: " + free + " velocity " + _velocity + " new postion " + _position);
		//re-enque the vehicle to wake up again at the next simulation timestep
		_ts.enqueue(MP.simulationTimeStep + _ts.currentTime(), this);
		//System.out.println("Enqued car(" + this.hashCode() + ") for wakeup at" + (_ts.currentTime() + MP.simulationTimeStep));
	}

	@Override
	public VehicleAcceptor getCurrentRoad() {
		return _currentRoad;
	}

	@Override
	public double getPosition() {
		return _position;
	}

	@Override
	public double getLength() {
		return _length;
	}

	@Override
	public double getBackPosition() {
		return _position - this.getLength();
	}

	@Override
	public double getBrakeDistance() {
		return _brakeDistance;
	}
	
	@Override
	public Color getColor() {
		return _color;
	}

	@Override
	public VehicleOrientation getOrientation() {
		return _orientation;
	}
	
	@Override
	public boolean sendCarToNextSeg(double requestedPostion) {
		//check if this road has a next segment set.
		  if (this.getCurrentRoad().getNextSeg(this) != null) {
			  
			  VehicleAcceptor next = this.getCurrentRoad().getNextSeg(this);
			  //now make sure there is space for it and that is will accept it
			  //System.out.println("Next " + next.getClass() + " " + this);
			  if (!isSpaceForCar(next) || !next.accept(this)) {
				  //System.out.println("Rejected by " + next.getClass() + " " + this + " Driveable? " + next.isDriveable(this) + " CARS: " + next.getCars());
				  return false;
			  }
			  //its been accepted, remove from the current road
			  this.getCurrentRoad().remove(this);
			  //update this object with the info from the new road
			  this.setCurrentVehicleAcceptor(next, requestedPostion);
			  
			  return true;
		  }   
		return false;
	}
	@Override
	public boolean isSpaceForCar(VehicleAcceptor r) {
		SortedSet<Vehicle> cars = r.getCars();
		if (cars == null) {
			return true;
		}
		for (Vehicle c: cars) {
			if (c.getBackPosition() <= this.getLength()) {
				//System.out.println("Not Enough Room for " + this + " b/c " + c);
				return false;
			}
		}
		return true;
	}

	public String toString() {
		return "Car(" + this.hashCode() + ") P=" + this.getPosition() + " V=" + _velocity;
	}

	@Override
	public int compareTo(Vehicle v) {
		if (v.getPosition() > this.getPosition()) {
			return 1;
		} else if (v.getPosition() < this.getPosition() ) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean isDisposed() {
		return _isDisposed;
	}

	@Override
	public void setDisposed() {
		_isDisposed = true;
		
	}
	
}
