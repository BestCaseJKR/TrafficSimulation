package model.VehicleAcceptor;

import java.util.Random;

import model.Agent.Agent;
import model.Agent.TimeServer;


/**
 * A light has a boolean state.
 */
public class Light implements Agent {
  
	private TimeServer _ts;

  public Light(TimeServer ts) {
	  _ts = ts;
	  _ts.enqueue(_ts.currentTime() + getState().getDuration(), this);
	  System.out.println("NEW LIGHT " + this + " Light CHange = " + (_ts.currentTime() + getState().getDuration()));
  } // Created only by this package
  private static Random rand = new Random();
  
  private LightState _state = LightState.values()[rand.nextInt(LightState.values().length)];

  public LightState getState() {
    return _state;
  }
	@Override
	public void run() {
		_state = _state.getNext();
		_ts.enqueue(_ts.currentTime() + getState().getDuration(), this);
	}

}

