package model.VehicleAcceptor;

import model.Agent.TimeServer;
import model.Agent.TimeServerLinked;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleFactory;
import model.Vehicle.VehicleOrientation;
import junit.framework.Assert;
import junit.framework.TestCase;

public class IntersectionTEST extends TestCase {

	public void testGetEW_NSRoads() {
		
		TimeServer ts = new TimeServerLinked();
		Intersection i = (Intersection)VehicleAcceptorFactory.newIntersection(ts);
		
		VehicleAcceptor nsRoad = VehicleAcceptorFactory.newRoad();
		nsRoad.setOrientation(VehicleOrientation.North_South);
		i.setNSRoad(nsRoad);
		
		VehicleAcceptor ewRoad = VehicleAcceptorFactory.newRoad();
		ewRoad.setOrientation(VehicleOrientation.East_West);
		i.setEWRoad(ewRoad);
		
		
		Assert.assertTrue(nsRoad.equals(i.getNSRoad()));
		Assert.assertTrue(ewRoad.equals(i.getEWRoad()));
		
		
	}
	
	public void testGetNextSeg() {
		
		TimeServer ts = new TimeServerLinked();
		Intersection i = (Intersection)VehicleAcceptorFactory.newIntersection(ts);
		
		VehicleAcceptor nsRoad = VehicleAcceptorFactory.newRoad();
		nsRoad.setOrientation(VehicleOrientation.North_South);
		i.setNSRoad(nsRoad);
		
		VehicleAcceptor ewRoad = VehicleAcceptorFactory.newRoad();
		ewRoad.setOrientation(VehicleOrientation.East_West);
		i.setEWRoad(ewRoad);
		
		
		Assert.assertTrue(nsRoad.equals(i.getNSRoad()));
		Assert.assertTrue(ewRoad.equals(i.getEWRoad()));
		
		VehicleAcceptor leadInNS = VehicleAcceptorFactory.newRoad();
		leadInNS.setOrientation(VehicleOrientation.North_South);
		VehicleAcceptor leadInEW = VehicleAcceptorFactory.newRoad();
		leadInEW.setOrientation(VehicleOrientation.East_West);
		
		Vehicle v = VehicleFactory.newCar(ts, leadInNS);
		Vehicle v2 = VehicleFactory.newCar(ts, leadInEW);
		
		Assert.assertTrue(nsRoad.equals(i.getNextSeg(v)));
		Assert.assertTrue(ewRoad.equals(i.getNextSeg(v2)));
		
		
	}
	
	public void testGetSetLightController() {
		
		TimeServer ts = new TimeServerLinked();
		
		LightController l1 = new LightController(ts);
		
		Intersection i = (Intersection)VehicleAcceptorFactory.newIntersection(ts);
		
		i.setLightControl(l1);
		
		Assert.assertTrue(i.getLightControl().equals(l1));
		
		LightController l2 = new LightController(ts);
		
		
		i.setLightControl(l2);
		Assert.assertFalse(i.getLightControl().equals(l1));
		Assert.assertTrue(i.getLightControl().equals(l2));
		
	}
	
}
