package model;

import java.util.Random;

/**
 * Static class for model parameters.
 */
public class MP {
  private MP() {}
  
  public static double simulationTimeStep = .1;
  public static double simulationRunTime = 1000.0;
  public static GridDimensions grid = new GridDimensions(2,3);
  public static TrafficPattern simulationTrafficPatter = TrafficPattern.Alternating;
  public static Range carEntryRate = new Range(2, 25);
  public static Range roadSegmentLength = new Range(200,500);
  public static Range intersectionLength = new Range(10,15);
  public static Range carLength = new Range(5,10);
  /** Maximum car velocity, in meters/second */
  public static Range maxVelocity = new Range(10, 30);
  public static Range breakDistance = new Range(9, 10);
  public static Range stopDistance = new Range(0.5,5);
  public static Range carStopDistance = new Range(0.5,5);
  public static Range trafficLightGreenTime = new Range(10,20);
  public static Range trafficLightYellowTime = new Range(4,5);
  public static Range sourceGenerationDelay = new Range(10,15);
  
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

