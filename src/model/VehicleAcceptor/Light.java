package model.VehicleAcceptor;

enum LightColor {
	Green,
	Yellow,
	Red
}

/**
 * A light has a boolean state.
 */
public class Light {

private LightColor _color;

	Light() {
		
		_color = LightColor.Green;
	}

	public void setColor(LightColor c) {
		_color = c;
	}
	
	public LightColor getColor() {
		return _color;
	}

}

