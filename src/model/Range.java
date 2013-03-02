package model;

import java.util.Random;

public class Range {
	
	public Range(double min, double max) {
		_min = min;
		_max = max;
	}
	
	public double _min;
	
	public double _max;
	
	private static Random rand = new Random();
	
	public double getDoubleInRange() {
		return _min + (_max - _min) * rand.nextDouble();
	}
}
