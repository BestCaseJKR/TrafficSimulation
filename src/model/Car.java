package model;

import java.awt.Color;
import java.util.Queue;


public class Car implements Vehicle, Agent {
	
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
		  //reset the position
		  this.setPosition(0);
		  //this.moveTo(requestedPostion);
		  double free = this.checkFreeSpaceAhead();
		  //if the road is clear AND we want to skip it, we need to pass the car along to the next road AGAIN
		  if (free == this.getCurrentRoad().getLength() && requestedPostion > free) {
			  if (sendCarToNextSeg(requestedPostion - free)) {
				  return;
			  } else {
				  //the send op failed, just move to the free space
				  this.setPosition(free);
			  }
		  } else if (requestedPostion > free) {
			  //our request was greater than the free space, but less than the road length, 
			  //take the free space
			  this.setPosition(free);
		  } else {
			  //our request was shorter than the length and the free space, request granted
			  this.setPosition(requestedPostion);
		  }
		  
		  
		  //this.setPosition(requestedPostion);
		  System.out.println("Added to VA: Current Postion: " + _position + " Requested: " + requestedPostion + " velocity: " + _velocity  + " in Road: " + this.getCurrentRoad() + " FREE = " + free);
	  }
	
	  void setPosition(double position) {
		  _position = position;
	  }
  
	  	public double checkFreeSpaceAhead() {
	  		
			//get a copy of all the vehicles in the VehicleAccpetor object
			Queue<Vehicle> cars = this.getCurrentRoad().getCars();
			//set pos to a base value, -1 in this car
			double pos = -1;
			//if no cars are found, return pos
			if (cars == null) {
				return pos;
			}
			
			//for each car...
			for	(Vehicle checkCar: cars) {
				//make sure that we arent checking ourself
				//then check to see if the car's back position would get in the way
				//and that the object isn't behind us to being with
				if (!this.equals(checkCar) && checkCar.getBackPosition() >= this.getBackPosition()	
				) {
					System.out.println("OBS " + checkCar + " obstructing " + this);
					//this object is in our way. store the next free block
					double tmpPos = checkCar.getBackPosition() - getPosition();
					System.out.println("OBJECT IN WAY " + tmpPos);
					if(tmpPos >= 0) {
						//assuming the free block is >= 0, we set tmpPos with its value the first time we receive one
						//pos will store the lowest value(thus the next free block) here
						if (pos == -1) {
							pos = tmpPos;
						}
					}
					//now test tmpPos and pos, take the smaller value.
					pos = (tmpPos < pos) ? tmpPos : pos;
				} else  {
					//if (!this.equals(checkCar)) System.out.println("NOT IN MY(" + this + " " + this.getPosition() + " r=" + toPosition + ") WAY " + checkCar + " " + checkCar.getBackPosition());
					
				}
			}
			//if pos has been set to something, return it
			if (pos != -1) {
				System.out.println("RM: " + this + " Return: " + pos);
				return pos;
			}
			//testing for a bug. TODO: Remove test
			if (pos < 0 && pos != -1) {
				System.out.println("WTF " + pos);
				System.exit(1);
			}
			//System.out.println("toPosition " + toPosition);
			//if this is hit, we know that pos was never set. the road should be open
			return this.getCurrentRoad().getLength() - this.getPosition();
	  		
	  	}
	  	/**
	  	 * Move the car to, at the most, the requested position
	  	 * @param toPosition
	  	 */
	  	private void moveTo(double toPosition) {
	  		//get the free space in front of us on this road
	  		double freeSpace = checkFreeSpaceAhead();
	  		//calculate the maximum move
	  		double maxMove = toPosition;
	  		//if the road is clear AND the maxMove is beyond the current road road. try to send to the next road
	  		if ((freeSpace + this.getPosition()) >= this.getCurrentRoad().getLength()
	  			&& (this.getPosition() + maxMove) >= this.getCurrentRoad().getLength()	)  {
	  			//send to the next segment and request the appropriate space.
	  			if (sendCarToNextSeg(maxMove - freeSpace)) {
		    		  System.out.println("Sent car to next seg " + this);
		    		  //_ts.enqueue(MP.simulationTimeStep + _ts.currentTime(), this);
		    		  return;
		    	} else {
		    		//the car was rejected by the next road! set the max move to be the 
		    		//free space...
		    		maxMove = freeSpace;
		    	}
	  		}
	  		
	  		//calculate velocity
	  		double velocity = (_velocity / (_brakeDistance - _stopDistance)) * (freeSpace - _stopDistance);
	  		System.out.println("V1 = " + velocity);
	  		velocity = Math.max(0, velocity);
	  		System.out.println("V2 = " + velocity);
	  		velocity = Math.min(_velocity, velocity);
	  		double step = (velocity * MP.simulationTimeStep);
	  		double nextPos = this.getPosition() + step;
	  		System.out.println("C: " + this + " nextPos " + nextPos + " step " + Math.round(step) + " vs MM " + maxMove + " velocity = " + velocity + " fs: " + freeSpace + " rL = " + this.getCurrentRoad().getLength() + " VAL = " + (velocity * MP.simulationTimeStep));

	  		
	  		this.setPosition(nextPos);
	  		
	  	}
	@Override
	public void run() {
			if (this.getCurrentRoad().getClass() == Intersection.class) {
				System.out.println(this + " is inside the intersection!");
			}
		//call moveTo and pass the maximum possible move for this car	
		 this.moveTo(_velocity * MP.simulationTimeStep);
	    //System.out.println("(" + _ts.currentTime() + ")Running Car: " + this.toString() + " free: " + free + " velocity " + _velocity + " new postion " + _position);
		//re-enque the vehicle to wake up again at the next simulation timestep
		_ts.enqueue(MP.simulationTimeStep + _ts.currentTime(), this);
		//System.out.println("Enqued car for wakeup at" + (_ts.currentTime() + MP.simulationTimeStep));
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
				  System.out.println("Rejected by " + next.getClass() + " " + this + " Driveable? " + next.isDriveable(this));
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
	/**
	 * Request Move works for only the current road! I.e. this will not
	 * send a vehicle to its next road if the length is exceeded!
	 */
	@Override
	public double requestMove(double requestedMove) {
		//first test to see if the current postion and the request exceed the length of the road
		if ((this.getPosition() + requestedMove) > this.getCurrentRoad().getLength()) {
			//if so, set requestedMove to be the maximum length of the road.
			requestedMove = this.getCurrentRoad().getLength() - this.getPosition();
			
		}
		//now calculate the open space in front of us
		double opening = calculateOpenSpace(requestedMove);
		double velocity = opening;
		//now calculate the velocity
		
		double t = (_velocity / (_brakeDistance - _stopDistance)) * (opening - _stopDistance);
		t = Math.max(0.0, t);
		t = Math.min(velocity, t);
		
	/*	if (opening <= _stopDistance) {
			System.out.println("STOP DISTANCE " + velocity + " SD " + _stopDistance);
			velocity = _stopDistance;
		} else if (opening <= _brakeDistance) {
			System.out.println("BREAK DISTANCE " + velocity + " BD " + _brakeDistance);
			velocity = opening/2;
		} else {
			System.out.println("PAST BREAK DISTANCE " + velocity);
			velocity = opening;
		}
	*/	 
		//now if the new position+rquest exceed the road length..
		if ((this.getPosition() + t) > this.getCurrentRoad().getLength()) {
			//return whatever is smaller, the open space or the max length of the road
			//TODO: This can be improved. Can probably return requested move?
			return (t < (this.getPosition()  - this.getCurrentRoad().getLength())) ? t : (this.getPosition()   - this.getCurrentRoad().getLength()) ;
		}
		if (velocity != t) {
			System.out.println("Returning velcotry of " + velocity + " compared to t: " + t + " ON " + this.getCurrentRoad().getCars());
			
		}
		//return the opening
		return velocity; 
	}

	@Override
	public boolean isSpaceForCar(VehicleAcceptor r) {
		Queue<Vehicle> cars = r.getCars();
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
	
	/**
	 * calculate the open space of the current road up to toPosition.
	 * If no vehicles occupy the space from this.getPosition() to toPostion,
	 * toPosition is returned and the vehicle is free to move to this space
	 * @param toPosition
	 * @return
	 */
	private double calculateOpenSpace(double toPosition) {
		//get a copy of all the vehicles in the VehicleAccpetor object
		Queue<Vehicle> cars = this.getCurrentRoad().getCars();
		//set pos to a base value, -1 in this car
		double pos = -1;
		//if no cars are found, return pos
		if (cars == null) {
			return pos;
		}
		
		//for each car...
		for	(Vehicle checkCar: cars) {
			//make sure that we arent checking ourself
			//then check to see if the car's back position would get in the way
			//and that the object isn't behind us to being with
			if (!this.equals(checkCar) &&
					checkCar.getBackPosition() <= (this.getPosition() + toPosition) && 
					checkCar.getBackPosition() >= this.getBackPosition()	
			) {
				//this object is in our way. store the next free block
				double tmpPos = checkCar.getBackPosition() - getPosition();
				//System.out.println("OBJECT IN WAY " + checkCar);
				if(tmpPos >= 0) {
					//assuming the free block is >= 0, we set tmpPos with its value the first time we receive one
					//pos will store the lowest value(thus the next free block) here
					if (pos == -1) {
						pos = tmpPos;
					}
				}
				//now test tmpPos and pos, take the smaller value.
				pos = (tmpPos < pos) ? tmpPos : pos;
			} else  {
				//if (!this.equals(checkCar)) System.out.println("NOT IN MY(" + this + " " + this.getPosition() + " r=" + toPosition + ") WAY " + checkCar + " " + checkCar.getBackPosition());
				
			}
		}
		//if pos has been set to something, return it
		if (pos != -1) {
			//System.out.println("RM: " + toPosition + " Return: " + pos);
			return pos;
		}
		//testing for a bug. TODO: Remove test
		if (pos < 0 && pos != -1) {
			System.out.println("WTF " + pos);
			System.exit(1);
		}
		//System.out.println("toPosition " + toPosition);
		//if this is hit, we know that pos was never set. the road should be open
		return toPosition;
	}
	
	/**
	 * calculate the open space of the current road in front of this car
	 * If no vehicles occupy the space from this.getPosition() to toPostion,
	 * toPosition is returned and the vehicle is free to move to this space
	 * @param toPosition
	 * @return
	 */
	private double calculateOpenSpace() {
		//get a copy of all the vehicles in the VehicleAccpetor object
		Queue<Vehicle> cars = this.getCurrentRoad().getCars();
		//set pos to a base value, -1 in this car
		double pos = -1;
		//if no cars are found, return pos
		if (cars == null) {
			return pos;
		}
		
		//for each car...
		for	(Vehicle checkCar: cars) {
			//make sure that we arent checking ourself
			//then check to see if the car's back position would get in the way
			//and that the object isn't behind us to being with
			if (!this.equals(checkCar) &&
					//checkCar.getBackPosition() <= this.getPosition()  && 
					checkCar.getPosition() >= this.getPosition()	
			) {
				//this object is in our way. store the next free block
				double tmpPos = checkCar.getBackPosition() - getPosition();
				//System.out.println("OBJECT IN WAY " + checkCar);
				if(tmpPos >= 0) {
					//assuming the free block is >= 0, we set tmpPos with its value the first time we receive one
					//pos will store the lowest value(thus the next free block) here
					if (pos == -1) {
						pos = tmpPos;
					}
				}
				//now test tmpPos and pos, take the smaller value.
				pos = (tmpPos < pos) ? tmpPos : pos;
			} else  {
				//if (!this.equals(checkCar)) System.out.println("NOT IN MY(" + this + " " + this.getPosition() + " r=" + toPosition + ") WAY " + checkCar + " " + checkCar.getBackPosition());
				
			}
		}
		//if pos has been set to something, return it
		if (pos != -1) {
			//System.out.println("RM: " + toPosition + " Return: " + pos);
			return pos;
		}
		//testing for a bug. TODO: Remove test
		if (pos < 0 && pos != -1) {
			System.out.println("WTF " + pos);
			System.exit(1);
		}
		//System.out.println("toPosition " + toPosition);
		//if this is hit, we know that pos was never set. the road should be open
		return this.getCurrentRoad().getLength() - this.getPosition();
	}
	
	public String toString() {
		return "Car(" + this.hashCode() + ") P=" + this.getPosition() + " V=" + _velocity;
	}
	
}
