package model.VehicleAcceptor;

import model.Vehicle.VehicleOrientation;

public enum LightState {
	GreenNS_RedEW(VehicleOrientation.North_South, false),
	YellowNS_RedEW(VehicleOrientation.North_South, true),
	RedNS_GreenEW(VehicleOrientation.East_West, false),
	RedNS_YellowEW(VehicleOrientation.East_West, true);
	
	private LightState(VehicleOrientation or, boolean yellow) {
		allowedOrientation = or;
		_isYellow = yellow;
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
