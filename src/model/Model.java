package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
   *  Each road has one {@link Car}.
   *
   */
  public Model(AnimatorBuilder builder, TimeServer t) {
    if (builder == null) {
      builder = new NullAnimatorBuilder();
    }
    _ts = t;
    _ts.addObserver(this);
    
    setup(builder, t, 2, 3);
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
	    Intersection[][] intersections = new Intersection[rows][columns];
	    Boolean reverse;
	    Light li;
	    // Add Lights
	    for (int i=0; i<rows; i++) {
	      for (int j=0; j<columns; j++) {
	    	li = new Light(_ts);
	        intersections[i][j] = new Intersection();
	        intersections[i][j].setLight(li);
	        //builder.addLight(li, i, j);
	        builder.addIntersection(intersections[i][j], i, j);
	        ts.enqueue(li.getState().getDuration(), li);
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
	        builder.addHorizontalRoad(l, i, j, eastToWest);
	        roads.add(l);
	        if ((j == 0 && eastToWest == false) || (j == columns && eastToWest )) {
	        	//if this is a brand new road being created, create a source too
	        	Source s = new Source(l, ts);
	        	//also add it to the agents array because its an agent
	        }
	        if ((j == columns && eastToWest == false) || (j == 0 && eastToWest)) {
	        	Sink sink = new Sink(ts);
	        	l.setNextSeg(sink);
	        }
	        if (j == 0) {
	        	if(eastToWest) {
	        		intersections[i][j].setEWRoad(l);
	        	} else {
	        		l.setNextSeg(intersections[i][j]);
	        	}
	        } else if (j == columns) {
	        	if(eastToWest) {
	        		l.setNextSeg(intersections[i][j-1]);
	        	} else {
	        		intersections[i][j-1].setEWRoad(l);
	        	}
	        } else {
	        	if(eastToWest) {
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
	        builder.addVerticalRoad(l, i, j, southToNorth);
	        roads.add(l);
	        if ((i == 0 && southToNorth == false) || (i == rows && southToNorth )) {
	        	//if this is a brand new road being created, create a source too
	        	Source source = new Source(l, ts);
	        	//also add it to the agents array because its an agent
	        	//_agents.add(source);
	        }
	        if ((i == rows && southToNorth == false) || (i == 0 && southToNorth)) {
	        	Sink sink = new Sink(ts);
	        	l.setNextSeg(sink);
	        }
	        if (i == 0) {
	        	if(southToNorth) {
	        		intersections[i][j].setNSRoad(l);
	        	} else {
	        		l.setNextSeg(intersections[i][j]);
	        	}
	        } else if (i == rows) {
	        	if(southToNorth) {
	        		l.setNextSeg(intersections[i-1][j]);
	        	} else {
	        		intersections[i-1][j].setNSRoad(l);
	        	}
	        } else {
	        	if(southToNorth) {
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
		System.out.println("Model UPDATE Called " + arg0.getClass());
		this.setChanged();
		this.notifyObservers(arg1);
		
	}
}
