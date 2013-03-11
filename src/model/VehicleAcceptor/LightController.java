package model.VehicleAcceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.MP;
import model.Agent.Agent;
import model.Agent.TimeServer;
/**
 * Controller object for Lights. Used by intersections to control + synch
 * the two lights.
 * @author johnreagan
 *
 */
public class LightController implements Agent  {
	/**
	 * a different set of values for the main properties of the intersection
	 */
	private Map<LightState, Double> durations = new HashMap<LightState,Double>() {
		private static final long serialVersionUID = 1L;
	{ 
		put(LightState.GreenNS_RedEW, MP.trafficLightGreenTime.getDoubleInRange());
		put(LightState.YellowNS_RedEW, MP.trafficLightYellowTime.getDoubleInRange());
		put(LightState.RedNS_GreenEW, MP.trafficLightGreenTime.getDoubleInRange());
		put(LightState.RedNS_YellowEW, MP.trafficLightYellowTime.getDoubleInRange());
	}};
	/**
	 * The East_West Light
	 */
	private Light _ewLight;
	/**
	 * the North-South light
	 */
	private Light _nsLight;
	  //random used to set the light state at a random value	
	  private static Random rand = new Random();
	  /**
	   * The state(LightState) of the controller
	   */
	  private LightState _state = LightState.values()[rand.nextInt(LightState.values().length)];
	/**
	 * The attached TimeServer
	 */
	private TimeServer _ts;
	/**
	 * Constructor. Create the EW and NS lights and enque this object with the TimeServer 
	 * for wakeup.
	 * @param ts
	 */
	LightController(TimeServer ts) {
		
		_ts = ts;
		_ewLight = new Light();
		_nsLight = new Light();

		_ts.enqueue(_ts.currentTime() + durations.get(_state), this);
		
	}
	/**
	 * Get the state of the light
	 * @return
	 */
	public LightState getState() {
		return _state;
	}
	/**
	 * Get the EW Light
	 * @return
	 */
	public Light getEWLight() { return _ewLight; }
	/**
	 * Get the NS light
	 * @return
	 */
	public Light getNSLight() { return _nsLight; }
	
	/**
	 * Run the light and transition to the next state
	 */
	public void run() {
		
		_state = _state.getNext();
		
		updateLights();
		
		_ts.enqueue(_ts.currentTime() + durations.get(_state), this);
		
	}
	/**
	 * Update the light and set the appropriate states.
	 * TODO Can be improved and state pattern better applied
	 */
	private void updateLights() {
		
		switch (_state) {
		case GreenNS_RedEW:
			_ewLight.setColor(LightColor.Green);
			_nsLight.setColor(LightColor.Red);
			break;
		case YellowNS_RedEW:
			_ewLight.setColor(LightColor.Yellow);
			_nsLight.setColor(LightColor.Red);
			break;
		case RedNS_GreenEW:
			_ewLight.setColor(LightColor.Red);
			_nsLight.setColor(LightColor.Green);
			break;
		case RedNS_YellowEW:
			_ewLight.setColor(LightColor.Yellow);
			_nsLight.setColor(LightColor.Green);
			break;			
			default:
				
				break;
		}
		
	}
	
	
}
