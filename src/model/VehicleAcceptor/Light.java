package model.VehicleAcceptor;
/**
 * LightColor enum showing possible values of a traffic light.
 * @author johnreagan
 *
 */
enum LightColor {
	Green,
	Yellow,
	Red
}

/**
 * Light object which stores a LightColor state
 * @author johnreagan
 *
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

