package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import model.Agent.Agent;
import model.Agent.Source;
import model.Agent.TimeServer;
import model.TrafficGrid.GridDimension;
import model.TrafficGrid.TrafficGridBuilder;
import model.Vehicle.Car;
import model.Vehicle.VehicleOrientation;
import model.VehicleAcceptor.Intersection;
import model.VehicleAcceptor.Light;
import model.VehicleAcceptor.Road;
import model.VehicleAcceptor.Sink;
import model.VehicleAcceptor.VehicleAcceptor;
import model.VehicleAcceptor.VehicleAcceptorFactory;

import util.Animator;

/**
 * An example to model for a simple visualization.
 * The model contains two roads.
 * See {@link #SimpleModel(AnimatorBuilder)}.
 */
public class Model extends Observable implements Observer {
  private TimeServer _ts;
  private Animator _animator;
  private boolean _disposed;

  /** Creates a model to be visualized using the <code>builder</code>.
   *  If the builder is null, no visualization is performed.
   *
   */
  public Model(AnimatorBuilder builder, TimeServer t) {
    if (builder == null) {
      builder = new NullAnimatorBuilder();
    }
    _ts = t;
    _ts.addObserver(this);
    
    setup(builder, t, MP.grid.rows, MP.grid.columns);
    _animator = builder.getAnimator();
    super.addObserver(_animator);

    
  }

  /**
   * Run the simulation for <code>duration</code> model seconds.
   */
  public void run(double duration) { 
	  _ts.run(duration);
  }
  public void enqueue(double t, Agent a)   { _ts.enqueue(t,a); }
  public double currentTime() { return _ts.currentTime(); }
  /**
   * Throw away this model.
   */
  public void dispose() {
    _animator.dispose();
    _disposed = true;
  }

  private void setup(AnimatorBuilder builder, TimeServer ts, int rows, int columns) {
	    List<VehicleAcceptor> roads = new ArrayList<VehicleAcceptor>();
	    
	    
	    TrafficGridBuilder tb = new TrafficGridBuilder(MP.simulationTrafficPatter, new GridDimension(rows, columns), ts);
	    
	    Intersection[][] intersections = tb.buildIntersections();
	    // Add Lights
	    for (int i=0; i<rows; i++) {
	      for (int j=0; j<columns; j++) {
	        builder.addIntersection(intersections[i][j], i, j);
	      }
	    }

	    // Add Horizontal Roads
	    boolean eastToWest = false;
	    Road prevSeg = null;
	    for (int i=0; i<rows; i++) {
	    	prevSeg = null;	
	      for (int j=0; j<=columns; j++) {
	        Road l = new Road();
	        //l.setOrientation(((eastToWest)? RoadOrientation.East_West: RoadOrientation.West_East ));
	        l.setOrientation(VehicleOrientation.East_West);
	        roads.add(l);
	        
	        if (MP.simulationTrafficPatter == TrafficPattern.Simple) {
	        	
	        	builder.addHorizontalRoad(l, i, j, false);
	        	
	        	if (j == 0) {
	        		//if this is a brand new road being created, create a source too
		        	Source s = new Source(l, ts);
	        	}
	        	if (j == columns) {
	        		Sink sink = (Sink)VehicleAcceptorFactory.newSink(ts);
		        	l.setNextSeg(sink);
	        	}
	        } else {
	        	
	        	builder.addHorizontalRoad(l, i, j, eastToWest);
	        	
	        	if ((j == 0 && eastToWest == false ) || (j == columns && eastToWest)) {
	        		//if this is a brand new road being created, create a source too
		        	Source s = new Source(l, ts);
	        	}
	        	if ((j == columns && eastToWest == false) || (j == 0 && eastToWest)) {
	        		Sink sink = (Sink)VehicleAcceptorFactory.newSink(ts);
		        	l.setNextSeg(sink);
	        	}
	        	
	        	
	        }
	        
	       
	        if (j == 0) {
	        	if(MP.simulationTrafficPatter == TrafficPattern.Alternating && eastToWest) {
	        		intersections[i][j].setEWRoad(l);
	        	} else {
	        		l.setNextSeg(intersections[i][j]);
	        	}
	        } else if (j == columns) {
	        	if(MP.simulationTrafficPatter == TrafficPattern.Alternating && eastToWest) {
	        		l.setNextSeg(intersections[i][j-1]);
	        	} else {
	        		intersections[i][j-1].setEWRoad(l);
	        	}
	        } else {
	        	if(MP.simulationTrafficPatter == TrafficPattern.Alternating && eastToWest) {
	        		intersections[i][j].setEWRoad(l);
	        		l.setNextSeg(intersections[i][j-1]);
	        	} else {
	        		l.setNextSeg(intersections[i][j]);
	        		intersections[i][j-1].setEWRoad(l);
	        	}
	        }
	        
	        prevSeg = l;
	      
	      }
	      eastToWest = !eastToWest;
	    }

	    // Add Vertical Roads
	    boolean southToNorth = false;
	    prevSeg = null;
	    for (int j=0; j<columns; j++) {
	    	prevSeg = null;
	      for (int i=0; i<=rows; i++) {
	    	  Road l = new Road();
	        //l.setOrientation(((southToNorth)? RoadOrientation.South_North: RoadOrientation.North_South ));
	        l.setOrientation(VehicleOrientation.North_South);
	        
	        roads.add(l);
	        if (MP.simulationTrafficPatter == TrafficPattern.Simple) {
	        	builder.addVerticalRoad(l, i, j, false);
	        	if (i == 0) {
	        		//if this is a brand new road being created, create a source too
		        	Source s = new Source(l, ts);
	        	}
	        	if (i == rows) {
	        		Sink sink = new Sink(ts);
		        	l.setNextSeg(sink);
	        	}
	        } else {
	        	builder.addVerticalRoad(l, i, j, southToNorth);
	        	if ((i == 0 && southToNorth == false ) || (i == rows && southToNorth)) {
	        		//if this is a brand new road being created, create a source too
		        	Source s = new Source(l, ts);
	        	}
	        	if ((i == rows && southToNorth == false) || (i == 0 && southToNorth)) {
	        		Sink sink = new Sink(ts);
		        	l.setNextSeg(sink);
	        	}
	        	
	        	
	        }
	        
	        if (i == 0) {
	        	if(MP.simulationTrafficPatter == TrafficPattern.Alternating && southToNorth) {
	        		intersections[i][j].setNSRoad(l);
	        	} else {
	        		l.setNextSeg(intersections[i][j]);
	        	}
	        } else if (i == rows) {
	        	if(MP.simulationTrafficPatter == TrafficPattern.Alternating && southToNorth) {
	        		l.setNextSeg(intersections[i-1][j]);
	        	} else {
	        		intersections[i-1][j].setNSRoad(l);
	        	}
	        } else {
	        	if(MP.simulationTrafficPatter == TrafficPattern.Alternating && southToNorth) {
	        		intersections[i][j].setNSRoad(l);
	        		l.setNextSeg(intersections[i-1][j]);
	        	} else {
	        		l.setNextSeg(intersections[i][j]);
	        		intersections[i-1][j].setNSRoad(l);
	        	}
	        }
	        
	        prevSeg = l;
	      }
	      southToNorth = !southToNorth;
	    }
  }

	@Override
	public void update(Observable arg0, Object arg1) {
		this.setChanged();
		this.notifyObservers(arg1);
		
	}
}
