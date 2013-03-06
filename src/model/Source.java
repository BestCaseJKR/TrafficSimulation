package model;

import java.util.List;

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
		      Car car = new Car(_ts, _next);
		      System.out.println("Added Car at (" + _ts.currentTime() + "): " + car);
		      //_ts.enqueue(_ts.currentTime() + MP.simulationTimeStep, car);
		      _next.accept(car);
		      car.setCurrentVehicleAcceptor(_next, car.getLength());
		      _ts.enqueue(_ts.currentTime() + _delay, this);

	}

}
