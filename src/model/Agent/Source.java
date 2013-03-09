package model.Agent;

import model.MP;
import model.Agent.TimeServer;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleFactory;
import model.VehicleAcceptor.VehicleAcceptor;


public class Source implements Agent {
	/**
	 * The attached TimeServer object
	 */
	private TimeServer _ts;
	private VehicleAcceptor _next;
	private double _delay = MP.sourceGenerationDelay.getDoubleInRange();
	public Source(VehicleAcceptor next, TimeServer ts) {
		if (next == null) {
			throw new IllegalArgumentException("Invalid Road Supplied"); 
		}
		_next = next;
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

}
