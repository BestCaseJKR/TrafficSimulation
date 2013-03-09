package model.VehicleAcceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.MP;
import model.Agent.Agent;
import model.Agent.TimeServer;

public class LightController implements Agent  {
	
	private Map<LightState, Double> durations = new HashMap<LightState,Double>() {
		private static final long serialVersionUID = 1L;
	{ 
		put(LightState.GreenNS_RedEW, MP.trafficLightGreenTime.getDoubleInRange());
		put(LightState.YellowNS_RedEW, MP.trafficLightYellowTime.getDoubleInRange());
		put(LightState.RedNS_GreenEW, MP.trafficLightGreenTime.getDoubleInRange());
		put(LightState.RedNS_YellowEW, MP.trafficLightYellowTime.getDoubleInRange());
	}};
	
	private Light _ewLight;
	private Light _nsLight;
	
	  private static Random rand = new Random();
	  
	  private LightState _state = LightState.values()[rand.nextInt(LightState.values().length)];
	
	private TimeServer _ts;
	
	LightController(TimeServer ts) {
		
		_ts = ts;
		_ewLight = new Light();
		_nsLight = new Light();

		_ts.enqueue(_ts.currentTime() + durations.get(_state), this);
		
	}
	
	public LightState getState() {
		return _state;
	}
	
	public Light getEWLight() { return _ewLight; }

	public Light getNSLight() { return _nsLight; }
	
	@Override
	public void run() {
		
		_state = _state.getNext();
		
		updateLights();
		
		_ts.enqueue(_ts.currentTime() + durations.get(_state), this);
		
	}

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
