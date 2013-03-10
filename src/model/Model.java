package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import model.Agent.Agent;
import model.Agent.TimeServer;
import model.TrafficGrid.GridDimension;
import model.TrafficGrid.TrafficGridBuilder;
import model.TrafficGrid.TrafficGridPattern;
import model.Vehicle.Car;
import model.Vehicle.VehicleOrientation;
import model.VehicleAcceptor.Intersection;
import model.VehicleAcceptor.Light;
import model.VehicleAcceptor.Road;
import model.VehicleAcceptor.Sink;
import model.VehicleAcceptor.Source;
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
    
    setup(builder, t, MP.grid.getRow(), MP.grid.getColumn());
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
	    
	    
	    TrafficGridBuilder tb = new TrafficGridBuilder(rows, columns, ts);

		tb.buildIntersections();
		tb.buildHorizontalRoads();
		tb.buildVerticalRoads();

		
		tb.populateAnimatorBuilder(builder);

  }

	@Override
	public void update(Observable arg0, Object arg1) {
		this.setChanged();
		this.notifyObservers(arg1);
		
	}
}
