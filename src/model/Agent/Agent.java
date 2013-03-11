package model.Agent;
/**
 * Interface used for objects which can be ran by a controller, such as a
 * TimeServer. The controller calls run() which causes the implementing object
 * to act.
 */
public interface Agent {
	/**
	 * Run agent action.
	 */
	public void run();
}
