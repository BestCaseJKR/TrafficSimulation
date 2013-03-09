package model.VehicleAcceptor;

import model.MP;
import model.Agent.Agent;
import model.Agent.TimeServer;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleOrientation;

public class Sink extends VehicleAcceptor implements Agent {
	
	public Sink (TimeServer ts) {
		_ts = ts;
		_ts.enqueue(_ts.currentTime()+MP.simulationTimeStep, this);
	}

	/**
	 * The attached TimeServer object
	 */
	private TimeServer _ts;
	
	@Override
	public void run() {
		for(Vehicle c: this.getCars()) {
			remove(c);
			c.setDisposed();
			c = null;
		}
		_ts.enqueue(_ts.currentTime() + MP.simulationTimeStep, this);
	}

	@Override
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


	@Override
	public double getLength() {
		return Double.MAX_VALUE;
	}

}
