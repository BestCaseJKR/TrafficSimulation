package model.VehicleAcceptor;

import model.MP;
import model.Agent.TimeServer;
import model.Agent.TimeServerLinked;
import junit.framework.Assert;
import junit.framework.TestCase;


public class SourceTEST extends TestCase {

	public void testLength() {
		TimeServer ts = new TimeServerLinked();
		VehicleAcceptor sink = VehicleAcceptorFactory.newSink(ts);
		
		Assert.assertTrue(sink.getLength() > 0);
		
	}
	
	public void testGetSetNextSegment() {
		TimeServer ts = new TimeServerLinked();
		VehicleAcceptor source = VehicleAcceptorFactory.newSource(ts);
		
		Road r = (Road) VehicleAcceptorFactory.newRoad();
		
		source.setNextSeg(r);
		
		Assert.assertTrue(source.getNextSeg(null).equals(r));
		
	}
	
	public void testVehiclesAdded() {
		
		TimeServer ts = new TimeServerLinked();
		
		VehicleAcceptor source = VehicleAcceptorFactory.newSource(ts);
		
		Road r = (Road) VehicleAcceptorFactory.newRoad();
		
		source.setNextSeg(r);
		
		ts.run(MP.carEntryRate.getMax() * 5);
		
		Assert.assertTrue(r.getCars().size() >= 5);
		
		
	}
	
}
