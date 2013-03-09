package model.VehicleAcceptor;

import model.Agent.TimeServer;

public class VehicleAcceptorBuilder {

	private VehicleAcceptorBuilder() {}
	
	public static VehicleAcceptor newRoad() {
		Road r= new Road();
		return r;
	}
	
	public static VehicleAcceptor newIntersection() {
		Intersection i = new Intersection();
		return i;
	}
	
	public static VehicleAcceptor newSink(TimeServer ts) {
		Sink s = new Sink(ts);
		return s;
	}
	
}
