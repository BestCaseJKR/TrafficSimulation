package model;
public enum LightState {
	GreenNS_RedEW(MP.trafficLightGreenTime.getDoubleInRange(), VehicleOrientation.North_South, false),
	YellowNS_RedEW(MP.trafficLightYellowTime.getDoubleInRange(), VehicleOrientation.North_South, true),
	RedNS_GreenEW(MP.trafficLightGreenTime.getDoubleInRange(), VehicleOrientation.East_West, false),
	RedNS_YellowEW(MP.trafficLightYellowTime.getDoubleInRange(), VehicleOrientation.East_West, true);
	
	private LightState(double dur, VehicleOrientation or, boolean yellow) {
		duration = dur;
		allowedOrientation = or;
		_isYellow = yellow;
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
	
	private boolean _isYellow;
	
	public boolean isYellow() {
		return _isYellow;
	}
}
