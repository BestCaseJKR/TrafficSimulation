package model.VehicleAcceptor;
/**
 * Simple enum representing the possible values that a road might use
 * to inform a vehicle if it is drivable or not.
 * @author johnreagan
 *
 */
public enum Drivability {
	Driveable,//all clear
	NotDriveable,//STOP!
	Caution//if you can stop, you should stop, otherwise proceed
}
