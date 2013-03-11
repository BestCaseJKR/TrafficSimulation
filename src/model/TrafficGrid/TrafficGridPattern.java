package model.TrafficGrid;


import model.Vehicle.VehicleOrientation;
import model.VehicleAcceptor.Intersection;
import model.VehicleAcceptor.VehicleAcceptor;
/**
 * Enum representing the different TrafficPatterns available.
 * Each implements the abstract method, linkRoad
 * @author johnreagan
 *
 */
public enum TrafficGridPattern {
	Simple {
		/**
		 * Link roads in the simple manner. Sources first, the roads/intersections, followed
		 * by the sink
		 */
		public void linkRoad(VehicleAcceptor[] roads,
				Intersection[] intersections, VehicleAcceptor source,
				VehicleAcceptor sink) {
			
			//set the source
			source.setNextSeg(roads[0]);
			roads[roads.length-1].setNextSeg(sink);
			for (int j=0; j<roads.length; j++) {
					if ((j+1) == roads.length) {
						
					} else {
						roads[j].setNextSeg(intersections[j]);
						
						if (roads[j].getOrientation() == VehicleOrientation.East_West) {
							intersections[j].setEWRoad(roads[j+1]);
						} else {
							intersections[j].setNSRoad(roads[j+1]);
						}
						
					}	      
			}
			
		}
	},
	/**
	 * Alternating traffic pattern
	 */
	Alternating {
		/**
		 * linkRoad method to link the supplied roads[] array together.
		 * Links Sink <- Roads/Intersections <- Source, so the sink is linked to the first
		 * elements and the source to the last.
		 */
		public void linkRoad(VehicleAcceptor[] roads,
				Intersection[] intersections, VehicleAcceptor source,
				VehicleAcceptor sink ) {
			
			//set the source
			source.setNextSeg(roads[roads.length - 1]);
			roads[0].setNextSeg(sink);
			
			for (int j=(roads.length - 1); j>=0; j--) {
					if (j > 0) {
						roads[j].setNextSeg(intersections[j - 1]);
						
						if (roads[j].getOrientation() == VehicleOrientation.East_West) {
							intersections[j-1].setEWRoad(roads[j-1]);
						} else {
							intersections[j-1].setNSRoad(roads[j-1]);
						}
						
					
					}	      
			}
			
		}

	};
	
	/**
	 * Method def for the state's linkRoad method.
	 * @param roads
	 * @param intersections
	 * @param source
	 * @param sink
	 */
	public abstract void linkRoad(VehicleAcceptor[] roads,
			Intersection[] intersections, VehicleAcceptor source,
			VehicleAcceptor sink);

}
