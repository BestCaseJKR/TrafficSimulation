package model.VehicleAcceptor;

import model.MP;
import model.Agent.Agent;
import model.Agent.TimeServer;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleFactory;
import model.Vehicle.VehicleOrientation;


public class Source extends VehicleAcceptor implements Agent  {
	/**
	 * The attached TimeServer object
	 */
	private TimeServer _ts;
	private VehicleAcceptor _next;
	private double _delay = MP.sourceGenerationDelay.getDoubleInRange();
	public Source(TimeServer ts) {
		_ts = ts;
		_ts.enqueue(ts.currentTime(), this);
	}
	public void run() {
		
		      Vehicle car = VehicleFactory.newCar(_ts, _next);
		      //_ts.enqueue(_ts.currentTime() + MP.simulationTimeStep, car);
		      _next.accept(car);
		      car.setCurrentVehicleAcceptor(_next, car.getLength());
		      _ts.enqueue(_ts.currentTime() + _delay, this);

	}
	
	public Drivability isDriveable(Vehicle c) {
		return Drivability.Driveable;
	}
	@Override
	public void setNextSeg(VehicleAcceptor next) {
		_next = next;
		
	}
	@Override
	public VehicleAcceptor getNextSeg(Vehicle c) {
		return _next;
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
		// TODO Auto-generated method stub
		return Double.MAX_VALUE;
	}

}
