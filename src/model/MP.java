package model;


/**
 * Static class for model parameters.
 */
public class MP {
  private MP() {}
  
  static {
	  setDefaults();
  }
  
  public static void setDefaults() {
	  simulationTimeStep = .1;
	  simulationRunTime = 1000.0;
	  grid = new GridDimensions(2,3);
	  simulationTrafficPatter = TrafficPattern.Alternating;
	  carEntryRate = new Range(2, 25);
	  roadSegmentLength = new Range(200,500);
	  intersectionLength = new Range(10,15);
	  carLength = new Range(5,10);
	  /** Maximum car velocity, in meters/second */
	  maxVelocity = new Range(10, 30);
	  breakDistance = new Range(9, 10);
	  stopDistance = new Range(0.5,5);
	  carStopDistance = new Range(0.5,5);
	  trafficLightGreenTime = new Range(10,20);
	  trafficLightYellowTime = new Range(4,5);
	  sourceGenerationDelay = new Range(10,15);
	 
  }
  
  
  public static double simulationTimeStep;
  public static double simulationRunTime;
  public static GridDimensions grid;
  public static TrafficPattern simulationTrafficPatter;
  public static Range carEntryRate;
  public static Range roadSegmentLength;
  public static Range intersectionLength;
  public static Range carLength;
  /** Maximum car velocity, in meters/second */
  public static Range maxVelocity;
  public static Range breakDistance;
  public static Range stopDistance;
  public static Range carStopDistance;
  public static Range trafficLightGreenTime;
  public static Range trafficLightYellowTime;
  public static Range sourceGenerationDelay;
  
  /** the delay used to space out car generation  */
  //public static double sourceGenerationDelay = 10;
  /** Length of cars, in meters */
  public static double baseCarLength = 10;
  /** Length of roads, in meters */
  public static double baseRoadLength = 200;
  public static double baseIntersectionLength = 50;
  public static double minMaxVelocity = 10;
  public static double maxMaxVelocity = 30;
  
  //public static double maxVelocity = Random.(maxMaxVelocity - minMaxVelocity + 1) + minMaxVelocity;
}  

