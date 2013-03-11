package model.VehicleAcceptor;

import junit.framework.Assert;
import junit.framework.TestCase;
import model.Agent.TimeServer;
import model.Agent.TimeServerLinked;
import model.Vehicle.Car;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleFactory;
import model.Vehicle.VehicleOrientation;

public class VehicleAcceptorTEST extends TestCase {

	private class VehicleAcceptorObj extends VehicleAcceptor {
	
		public Drivability isDriveable(Vehicle c) {
			return Drivability.Driveable;
		}
		public void setNextSeg(VehicleAcceptor next) {
			// TODO Auto-generated method stub
			
		}
		public VehicleAcceptor getNextSeg(Vehicle c) {
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
			return 0;
		}
		
	}
	
	public void testGetCars() {
		
		TimeServer ts = new TimeServerLinked();
		
		VehicleAcceptor r = new VehicleAcceptorObj();
		
		Vehicle c = VehicleFactory.newCar(ts, r);
		r.accept(c);
		Assert.assertTrue(r.getCars().size() == 1);
		Assert.assertTrue(c.equals(r.getCars().get(0)));
		
		for	(int i=0;i<10;i++) {
			r.accept(VehicleFactory.newCar(ts, r));
		}
		
		Assert.assertTrue(r.getCars().size() == 11);
		Assert.assertTrue(c.equals(r.getCars().get(0)));
		Assert.assertTrue(c == r.getCars().get(0));
		
		for	(int i=0;i<10;i++) {
			//TODO rewtite TEST!!!!
			Assert.assertTrue(r.getCars().get(0) != null);
		}
		
	}
	
	public void testAcceptAndRemove() {
		
		VehicleAcceptor r = new VehicleAcceptorObj();
		
		TimeServer ts = new TimeServerLinked();
		Car[] cars = new Car[10];
		for	(int i=0;i<10;i++) {
			
			cars[i] = (Car)VehicleFactory.newCar(ts, r);
			r.accept(cars[i]);
		}
		
		Assert.assertTrue(r.getCars().size() == 10);
		
		for	(int i=0;i<10;i++) {
			r.remove(cars[i]);
		}
		
		Assert.assertTrue(r.getCars().size() == 0);
		
		try {
			r.accept(null);
			Assert.fail();
		} catch(Exception e) {
			
		}
	}
	
}
