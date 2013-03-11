package model.VehicleAcceptor;

import model.MP;
import model.Agent.Agent;
import model.Agent.TimeServer;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleOrientation;
/**
 * Sink class used to remove cars which are added to it. Essentially if a car
 * sends itself to a sink, it is removed from the sim.
 * @author johnreagan
 *
 */
public class Sink extends VehicleAcceptor implements Agent {
	
	public Sink (TimeServer ts) {
		_ts = ts;
		_ts.enqueue(_ts.currentTime()+MP.simulationTimeStep, this);
	}

	/**
	 * The attached TimeServer object
	 */
	private TimeServer _ts;
	
	/**
	 * Action of the sink. Remove any cars found in the cars list
	 */
	public void run() {
		Object[] i = this.getCars().toArray();
		for(Object o: i) {
			Vehicle v = (Vehicle)o;
			remove(v);
			v.setDisposed();
			v = null;
		}
		_ts.enqueue(_ts.currentTime() + MP.simulationTimeStep, this);
	}

	/**
	 * Get the drivability of the road. Always return Drivable
	 */
	public Drivability isDriveable(Vehicle c) {
		// TODO Auto-generated method stub
		return Drivability.Driveable;
	}

	@Override
	public void setNextSeg(VehicleAcceptor next) {
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


	/**
	 * Return the max value for a double
	 */
	public double getLength() {
		return Double.MAX_VALUE;
	}

}
