package model;

import java.util.Random;
/**
 * Class used to work with a range of doubles.
 * Can return a double within the provided range
 * @author johnreagan
 *
 */
public class Range {
	/**
	 * Contructor. Sets the range min and max
	 * @param min - minimum value of range
	 * @param max - max value of range
	 */
	public Range(double min, double max) {
		_min = min;
		_max = max;
	}
	/**
	 * the lower bound of the range.
	 * @constraint - must be <= _max
	 */
	private double _min;
	/**
	 * Get the minimum value of the range
	 * @return
	 */
	public double getMin() {
		return _min;
	}
	/**
	 * Set the minimum value of the range
	 * @param min
	 */
	public void setMin(double min) {
		this._min = min;
	}
	/**
	 * the maximum/upper bound of the range
	 * @constraint - must be >= _min
	 */
	private double _max;
	/**
	 * get the max value of the range
	 * @return
	 */
	public double getMax() {
		return _max;
	}
	/**
	 * set the max value of the range
	 * @param max
	 */
	public void setMax(double max) {
		this._max = max;
	}
	/**
	 * a Random instance to facilitate selecting a random number in the range
	 */
	private static Random rand = new Random();
	/**
	 * return a double that is >= _min and <= _max
	 * @return
	 */
	public double getDoubleInRange() {
		return _min + (_max - _min) * rand.nextDouble();
	}
	/**
	 * toString
	 */
	public String toString() {
		return " Min: " + _min + ", " + " Max: " + _max;
	}
}
