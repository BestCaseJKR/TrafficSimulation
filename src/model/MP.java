package model;

import model.TrafficGrid.GridDimension;
import model.TrafficGrid.TrafficGridPattern;


/**
 * Static class for model parameters.
 */
public class MP {
  private MP() {}
  /**
   * Static initializer. set the defaults, enables the default
   * values to be stored in one place and reset if necessary.
   */
  static {
	  setDefaults();
  }
  /**
   * populate the variables with the default values
   */
  public static void setDefaults() {
	  simulationTimeStep = .1;
	  simulationRunTime = 1000.0;
	  grid = new GridDimension(2,3){{ }};
	  simulationTrafficPattern = TrafficGridPattern.Alternating;
	  carEntryRate = new Range(2, 25);
	  roadSegmentLength = new Range(200,500);
	  intersectionLength = new Range(10,15);
	  carLength = new Range(5,10);
	  /** Maximum car velocity, in meters/second */
	  maxVelocity = new Range(10, 30);
	  breakDistance = new Range(9, 10);
	  stopDistance = new Range(0.5,5);
	  trafficLightGreenTime = new Range(10,20);
	  trafficLightYellowTime = new Range(4,5);
	 
  }
  
  /**
   * The timestep of the simultation. Used to control the granularity of the program.
   */
  public static double simulationTimeStep;
  /** The total runtime in seconds of the simultation */
  public static double simulationRunTime;
  /** a GridDimension object to store the dimensions of the traffic grid. */
  public static GridDimension grid;
  /** The traffic pattern on the simulation. */
  public static TrafficGridPattern simulationTrafficPattern;
  /** The entry/creation rate of car source. */
  public static Range carEntryRate;
  /** Length of regular road segments */
  public static Range roadSegmentLength;
  /** Length of intersections */
  public static Range intersectionLength;
  /** Length of car objects */
  public static Range carLength;
  /** Maximum car velocity, in meters/second */
  public static Range maxVelocity;
  /** Breaking distance of a vehicle. If obstacle is w/in, braking begins. */
  public static Range breakDistance;
  /** the stopping distance. If obstacle w/in, car stops */
  public static Range stopDistance;
  /** Duration of a green light */
  public static Range trafficLightGreenTime;
  /** Duration of Yellow Lights */
  public static Range trafficLightYellowTime;
  
  /** the delay used to space out car generation  */
  //public static double sourceGenerationDelay = 10;
  /** Length of cars, in meters */
  public static double baseCarLength = 10;
  /** Length of roads, in meters */
  public static double baseRoadLength = 200;
  public static double baseIntersectionLength = 50;
  public static double minMaxVelocity = 10;
  public static double maxMaxVelocity = 30;

}

