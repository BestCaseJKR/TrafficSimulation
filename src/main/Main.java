package main;

import model.*;
import model.swing.SwingAnimatorBuilder;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SwingAnimatorBuilder build = new SwingAnimatorBuilder();
		TimeServerQueue ts = new TimeServerQueue();
		
		Model m = new Model(build, ts);
		m.run(500);
	}

}
