package Animator;

import model.VehicleAcceptor.Intersection;
import model.VehicleAcceptor.Light;
import model.VehicleAcceptor.VehicleAcceptor;
import util.Animator;

/**
 * Null object for {@link AnimatorBuilder}.
 * Made public to allow access for integration testing in main package
 */
public class NullAnimatorBuilder implements AnimatorBuilder {
  public Animator getAnimator() { return new NullAnimator(); }
  public void addLight(Light d, int i, int j) { }
  public void addHorizontalRoad(VehicleAcceptor l, int i, int j, boolean eastToWest) { }
  public void addVerticalRoad(VehicleAcceptor l, int i, int j, boolean southToNorth) { }
  public void addIntersection(Intersection intersection, int i, int j) {	}
}