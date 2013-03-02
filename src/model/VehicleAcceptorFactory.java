package model;

public class VehicleAcceptorFactory {
	private VehicleAcceptorFactory() { }
	
	public static VehicleAcceptor generateRoad() {
		Road r= new Road();
		return r;
	}
	
	public static VehicleAcceptor generateSink(TimeServer ts) {
		Sink s = new Sink(ts);
		return s;
	}
}
