package model;
import junit.framework.Assert;
import junit.framework.TestCase;
public class CarTEST extends TestCase {
	
	public void testPostion() {
		
		TimeServer ts = new TimeServerLinked();
		VehicleAcceptor va = new Road();
		
		Car c = new Car(ts, va);
		va.accept(c);
		//test the car is added properly
		Assert.assertTrue(c.getBackPosition() == 0.0);
		Assert.assertTrue(c.getPosition() == c.getLength());
		
		ts.run(1000);
		
		Assert.assertTrue(c.getPosition() > c.getLength());
		
		Car c2 = new Car (ts, va);
		va.accept(c2);
		
		ts.run(10);
		
		Car c3 = new Car (ts, va);
		va.accept(c3);
		
		ts.run(1000);
		Assert.assertTrue(c.getPosition() > c2.getPosition());
		System.out.println("C2 " + c2.getPosition() + " C3 " + c3.getPosition());
		Assert.assertTrue(c2.getPosition() > c3.getPosition());
		
		
		
	}
	
	public void testMove() {
		TimeServer ts = new TimeServerLinked();
		Road r = new Road();
		
		Car c1 = new Car(ts, r);
		r.accept(c1);
		
		c1.run();
		
		Assert.assertTrue(c1.getPosition() > 0);
		double c1Orig = c1.getPosition();
		
		
		Car c2 = new Car(ts, r);
		r.accept(c2);
		c2.run();
		
		
		
		for (int i =0;i<100;i++) {
			c1.run();
			c2.run();
		}
		
		Car c3 = new Car(ts, r);
		r.accept(c3);
		for (int i =0;i<100;i++) {
			c1.run();
			c2.run();
			c3.run();
		}
		System.out.println("C1: " + c1 + " c1Orig " + c1Orig);
		Assert.assertTrue(c1.getPosition() > c1Orig);
		Assert.assertTrue(c2.getPosition() > 0);
		Assert.assertTrue(c3.getPosition() > 0);
		
		Assert.assertTrue(c1.getPosition() > c2.getPosition());
		System.out.println("C2: " + c2 + " C3: " + c3);
		Assert.assertTrue(c2.getPosition() > c3.getPosition());
		
		
	}
	
	public void testCheckFreeSpace() {
		
		TimeServer ts = new TimeServerLinked();
		Road r = new Road();
		
		Car c1 = new Car(ts, r);
		Car c2 = new Car(ts, r);
		Car c3 = new Car(ts, r);
		
		r.accept(c1);
		c1.setPosition(100);
		r.accept(c2);
		c2.setPosition(50);
		
		Assert.assertEquals(c2.checkFreeSpaceAhead(), (c1.getBackPosition() - c2.getPosition()));
		
	}
	
	
	
	
}
