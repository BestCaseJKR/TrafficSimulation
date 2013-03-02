package model;
public enum LightState {
	GreenNS_RedEW(MP.trafficLightGreenTime.getDoubleInRange(), VehicleOrientation.North_South),
	YellowNS_RedEW(MP.trafficLightYellowTime.getDoubleInRange(), VehicleOrientation.North_South),
	RedNS_GreenEW(MP.trafficLightGreenTime.getDoubleInRange(), VehicleOrientation.East_West),
	RedNS_YellowEW(MP.trafficLightYellowTime.getDoubleInRange(), VehicleOrientation.East_West);
	
	private LightState(double dur, VehicleOrientation or) {
		duration = dur;
		allowedOrientation = or;
	}
	
	private double duration;
	
	public double getDuration() {
		return duration;
	}
	
	private VehicleOrientation allowedOrientation;
	
	public VehicleOrientation getAllowedOrientation() {
		return this.allowedOrientation;
	}
	
	public LightState getNext() {
		return values()[(ordinal()+1) % values().length];
	}
}
