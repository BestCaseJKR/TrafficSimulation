package Animator;

import model.Model;
import model.VehicleAcceptor.Intersection;
import model.VehicleAcceptor.Light;
import model.VehicleAcceptor.VehicleAcceptor;
import util.Animator;

/** 
 * An interface for building a {@link Animator} for this {@link Model}.
 */
public interface AnimatorBuilder {
  /**
   *  Returns the {@link Animator}.
   *  This method may be called only once; subsequent calls throw an
   *  {@link IllegalStateException}.
   */
  public Animator getAnimator();
  /**
   *  Add the {@link Light} to the display at position <code>i,j</code>.
   */
  public void addLight(Light d, int i, int j);
  /**
   * add an intersection to the builder so it can be animated properly.
   * @param i
   */
  public void addIntersection(Intersection intersection, int i, int j);
  /**
   *  Add the horizontal {@link RoadSegment} to the display, west of position <code>i,j</code>.
   *  If <code>eastToWest</code> is true, then road position 0 is the eastmost position.
   *  If <code>eastToWest</code> is false, then road position 0 is the westmost position.
   */
  public void addHorizontalRoad(VehicleAcceptor l, int i, int j, boolean eastToWest);
  /**
   *  Add the vertical {@link RoadSegment} to the display, north of position <code>i,j</code>.
   *  If <code>southToNorth</code> is true, then road position 0 is the southmost position.
   *  If <code>southToNorth</code> is false, then road position 0 is the northmost position.
   */
  public void addVerticalRoad(VehicleAcceptor l, int i, int j, boolean southToNorth);
}




