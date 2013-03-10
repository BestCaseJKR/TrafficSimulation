package model.VehicleAcceptor;

import model.Agent.TimeServer;

public class VehicleAcceptorFactory {
	private VehicleAcceptorFactory() { }
	
	public static VehicleAcceptor newRoad() {
		Road r= new Road();
		return r;
	}
	
	public static VehicleAcceptor newIntersection(TimeServer ts) {
		Intersection i = new Intersection();
		LightController lc = new LightController(ts);
		i.setLightControl(lc);
		return i;
	}

	public static VehicleAcceptor newSource(TimeServer ts) {
		Source s = new Source(ts);
		return s;
	}
	
	public static VehicleAcceptor newSink(TimeServer ts) {
		Sink s = new Sink(ts);
		return s;
	}
}
