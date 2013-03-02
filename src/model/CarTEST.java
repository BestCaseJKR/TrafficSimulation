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
		
		Assert.assertTrue(c.getPosition() > 0);
		
		Car c2 = new Car (ts, va);
		va.accept(c2);
		Car c3 = new Car (ts, va);
		va.accept(c3);
		
		ts.run(1000);
		Assert.assertTrue(c.getPosition() > c2.getPosition());
		Assert.assertTrue(c2.getPosition() > c3.getPosition());
		
		
		
	}
	
	
	
	
	
}
