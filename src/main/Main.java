package main;

/**
 * Main class for Traffic simulator.
 * Simply begins a controller object and ends itself when the controller is complete.
 */
public class Main {

	
	public static void main(String[] args) {
				 Control control = new Control();
				 control.run();
			    System.exit(0);
		}


}
