package model.TrafficGrid;

import Animator.AnimatorBuilder;

/**
 * Interface to represent the traffic grid which traffic is simulated.
 * Basic responsibility is to provide access to the grid as a whole as its being constructed.
 * @author John
 * 
 * CURRENTLY UNUSED!
 */
public interface TrafficGrid {
	
	public void setGridDimensions(GridDimension dimensions);
	
	public GridDimension getGridDimensions();
	
	public void populateBuilder(AnimatorBuilder builder);
	
}
