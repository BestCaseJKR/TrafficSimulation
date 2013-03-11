package model.VehicleAcceptor;

import java.util.Observer;


import model.MP;
import model.Agent.Agent;
import model.Agent.TimeServer;
import model.Agent.TimeServerLinked;
import model.Vehicle.Car;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleFactory;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RoadTEST extends TestCase {
	
	
	public void testLength() {
		
		VehicleAcceptor r = VehicleAcceptorFactory.newRoad();
		
		Assert.assertTrue(r.getLength() > MP.roadSegmentLength.getMin());
		Assert.assertTrue(r.getLength() < MP.roadSegmentLength.getMax());
		
	}
	
	public void testGetSetNextSegment() {
		
		VehicleAcceptor[] roads = new VehicleAcceptor[10];
		
		for (int i = 0; i < 10; i++) {
			VehicleAcceptor r = VehicleAcceptorFactory.newRoad();
			roads[i] = r;
			if (i > 0) {
				roads[i-1].setNextSeg(r);
			}
		}
		
		for (int i =0; i < 9; i++) {
			Assert.assertTrue(roads[i].getNextSeg(null) != null);
		}
		Assert.assertTrue(roads[9].getNextSeg(null) == null);
		
	}

}
