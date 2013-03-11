package model.VehicleAcceptor;

import model.Agent.TimeServer;
import model.Agent.TimeServerLinked;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleFactory;
import junit.framework.Assert;
import junit.framework.TestCase;


public class SinkTEST extends TestCase {

	public void testLength() {
		TimeServer ts = new TimeServerLinked();
		VehicleAcceptor sink = VehicleAcceptorFactory.newSink(ts);
		
		Assert.assertTrue(sink.getLength() > 0);
		
	}
	
	public void testGetSetNextSegment() {
		TimeServer ts = new TimeServerLinked();
		VehicleAcceptor sink = VehicleAcceptorFactory.newSink(ts);
		
		Road r = (Road) VehicleAcceptorFactory.newRoad();
		
		sink.setNextSeg(r);
		
		Assert.assertTrue(sink.getNextSeg(null) == null);
		
	}
	
	public void testVehiclesRemoved() {
		
		TimeServer ts = new TimeServerLinked();
		
		VehicleAcceptor sink = VehicleAcceptorFactory.newSink(ts);
		
		Vehicle[] cars = new Vehicle[5];
		
		for (int i = 0; i < cars.length; i++) {
			cars[i] = VehicleFactory.newCar(ts, sink);
			sink.accept(cars[i]);
		}
		
		Assert.assertTrue(sink.getCars().size() == 5);
		ts.run(10);
		
		Assert.assertTrue(sink.getCars().size() == 0);
		
	}
	
}
