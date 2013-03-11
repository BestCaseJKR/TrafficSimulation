package main;

import Animator.NullAnimatorBuilder;
import model.Agent.TimeServer;
import model.Agent.TimeServerLinked;
import model.TrafficGrid.TrafficGridBuilder;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleFactory;
import model.Vehicle.VehicleOrientation;
import model.VehicleAcceptor.Intersection;
import model.VehicleAcceptor.VehicleAcceptor;
import model.VehicleAcceptor.VehicleAcceptorFactory;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TEST1 extends TestCase {

	public TEST1(String name) {
		super(name);
	}
	
	public void test1() {
		//create a timeserver to test with
		TimeServer ts = new TimeServerLinked();
		//create a traffic grid builder with 2 rows and 3 columns
		TrafficGridBuilder tgb = new TrafficGridBuilder(2,3, ts);

		Assert.assertTrue(tgb.getHorizontalRoads().length == 2);
		Assert.assertTrue(tgb.getVerticalRoads().length == 3);
		Assert.assertTrue(tgb.getIntersections().length == 2);
		//populate a null animator builder
		tgb.populateAnimatorBuilder(new NullAnimatorBuilder());

		ts.run(500);
		
		VehicleAcceptor[] street1 = tgb.getHorizontalRoads()[0];
		boolean foundCars = false;
		for(VehicleAcceptor r: street1) {
			if (r.getCars().size() > 0) {
				foundCars = true;
			}
		}
		Assert.assertTrue(foundCars);
		
		//now do something a little more manual and controlled
		//reset the timeserver
		ts = new TimeServerLinked();
		
		
		//create the intersection
		Intersection is = (Intersection) VehicleAcceptorFactory.newIntersection(ts);
		
		VehicleOrientation o = VehicleOrientation.East_West;
		VehicleAcceptor first = null;
		//create and hook up the roads
		for(int i=0;i<2;i++) {
			//each row
			VehicleAcceptor source = VehicleAcceptorFactory.newSource(ts);
			VehicleAcceptor sink = VehicleAcceptorFactory.newSink(ts);
			VehicleAcceptor r1 = VehicleAcceptorFactory.newRoad();
			first = r1;
			VehicleAcceptor r2 = VehicleAcceptorFactory.newRoad();
			r1.setOrientation(o);r2.setOrientation(o);
			source.setNextSeg(r1);
			r1.setNextSeg(is);
			r2.setNextSeg(sink);
			if (o == VehicleOrientation.East_West) {
				is.setEWRoad(r2);
			} else {
				is.setNSRoad(r2);
			}

			
			//we know this will only run twice so it effectively does, o = !o;
			o = VehicleOrientation.North_South;
		}
		
		Vehicle v1 = VehicleFactory.newCar(ts, first);
		ts.run(10);
		Vehicle v2 = VehicleFactory.newCar(ts, first);
		ts.run(10);
		Assert.assertTrue(v1.getBackPosition() > v2.getPosition());
		
		
		
		
		
	}
	
}
