package model.VehicleAcceptor;

import java.util.Observer;


import model.Agent.Agent;
import model.Agent.TimeServer;
import model.Vehicle.Car;
import model.Vehicle.Vehicle;
import model.Vehicle.VehicleFactory;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RoadTEST extends TestCase {

	public void testGetCars() {
		
		Road r = new Road();
		DummyTS ts = new DummyTS();
		
		
		Car c = (Car)VehicleFactory.newCar(ts, r);
		r.accept(c);
		Assert.assertTrue(r.getCars().size() == 1);
		Assert.assertTrue(c.equals(r.getCars().first()));
		
		for	(int i=0;i<10;i++) {
			r.accept(VehicleFactory.newCar(ts, r));
		}
		
		Assert.assertTrue(r.getCars().size() == 11);
		Assert.assertTrue(c.equals(r.getCars().first()));
		Assert.assertTrue(c == r.getCars().first());
		
		for	(int i=0;i<10;i++) {
			//TODO rewtite TEST!!!!
			Assert.assertTrue(r.getCars().first() != null);
		}
		
	}
	
	public void testAcceptAndRemove() {
		
		Road r = new Road();
		DummyTS ts = new DummyTS();
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
	
	
	class DummyTS implements TimeServer {

		@Override
		public double currentTime() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void enqueue(double waketime, Agent thing) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void run(double duration) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addObserver(Observer o) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void deleteObserver(Observer o) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
