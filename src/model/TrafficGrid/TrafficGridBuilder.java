package model.TrafficGrid;

import Animator.AnimatorBuilder;
import model.MP;
import model.Agent.TimeServer;
import model.Vehicle.VehicleOrientation;
import model.VehicleAcceptor.Intersection;
import model.VehicleAcceptor.Sink;
import model.VehicleAcceptor.Source;
import model.VehicleAcceptor.VehicleAcceptor;
import model.VehicleAcceptor.VehicleAcceptorFactory;
/**
 * Class used to house the logic in building a traffic grid.
 * Currently does not return an actual traffic grid object.
 * Instead it builds the roads and can populate an Animator builder.
 * @author johnreagan
 *
 */
public class TrafficGridBuilder {
	/**
	 * The Dimensions of the traffic grid
	 */
	private final GridDimension _gridDimensions;
	/**
	 * The timeserver used for the simulation
	 */
	private final TimeServer _ts;
	/**
	 * An array of the intersections in the grid
	 */
	private Intersection[][] _intersections;
	/**
	 * An array of the horizontal roads in the grid
	 */
	private VehicleAcceptor[][] _horizontalRoads; // rows -> columns
	/**
	 * an array of the vertical roads of the grid
	 */
	private VehicleAcceptor[][] _verticalRoads; // columns -> rows
	/**
	 * return the intersections
	 * @return
	 */
	public Intersection[][] getIntersections() {
		return _intersections;
	}
/**
 * return the horizontal roads in the grid
 * @return
 */
	public VehicleAcceptor[][] getHorizontalRoads() {
		return _horizontalRoads;
	}
/**
 * return the vertical roads in the grid
 * @return
 */
	public VehicleAcceptor[][] getVerticalRoads() {
		return _verticalRoads;
	}
	/**
	 * Constructor. Takes the rows, columns and the TimeServer to be used in the 
	 * Simulation.
	 * @param rows
	 * @param columns
	 * @param ts
	 */
	public TrafficGridBuilder(int rows, int columns, TimeServer ts) {
		//create a new grid builder
		_gridDimensions = new GridDimension(rows, columns) {
			
		};
		
		_ts = ts;
		this.setupGrid();
	}
	/**
	 * Begin setup of traffic grid. Call builder methods
	 */
	private void setupGrid() {
		this.buildIntersections();
		this.buildHorizontalRoads();
		this.buildVerticalRoads();
	}
	
	/**
	 * Build the intersections for the simulation
	 */
	private void buildIntersections() {
		
		//initialize
	    _intersections = new Intersection[_gridDimensions.getRow()][_gridDimensions.getColumn()];
	    // Add organize by [rows][column]
	    for (int i=0; i<_gridDimensions.getRow(); i++) {
	      for (int j=0; j<_gridDimensions.getColumn(); j++) {
	    	  //create a new intersection
	    	  _intersections[i][j] = (Intersection)VehicleAcceptorFactory.newIntersection(_ts);
	      }
	    }
		
	}
	/**
	 * Build the horizontal roads for the grid
	 */
	private void buildHorizontalRoads() {
		// Add Horizontal Roads
		_horizontalRoads = new VehicleAcceptor[_gridDimensions.getRow()][_gridDimensions.getColumn()+1];
		 //[row][column]
		for (int i=0; i<_gridDimensions.getRow(); i++) {
	      for (int j=0; j<=_gridDimensions.getColumn(); j++) {
	        VehicleAcceptor l = VehicleAcceptorFactory.newRoad();
	        //l.setOrientation(((eastToWest)? RoadOrientation.East_West: RoadOrientation.West_East ));
	        l.setOrientation(VehicleOrientation.East_West);
	        _horizontalRoads[i][j] = l;
	      }
	    }
	}
	/**
	 * Build the vertical roads
	 */
	private void buildVerticalRoads() {
		_verticalRoads = new VehicleAcceptor[_gridDimensions.getColumn()][_gridDimensions.getRow()+1];
		//[column][row]
	    for (int j=0; j<_gridDimensions.getColumn(); j++) {
	      for (int i=0; i<=_gridDimensions.getRow(); i++) {
	    	  VehicleAcceptor l = VehicleAcceptorFactory.newRoad();
	        l.setOrientation(VehicleOrientation.North_South);
	        _verticalRoads[j][i] = l;
	      }
	    }
	    
	}	
	/**
	 * Method used to link the array of roads together based on the traffic pattern.
	 * Uses alternating to call the linkRoad
	 * @param roads
	 * @param intersections
	 * @param source
	 * @param sink
	 * @param alternating
	 */
	private void linkRoad(VehicleAcceptor[] roads,
				Intersection[] intersections, VehicleAcceptor source,
				VehicleAcceptor sink, TrafficGridPattern alternating) {
		//pass the logic to the state
		alternating.linkRoad(roads, intersections, source, sink);
	}
	/**
	 * Populate the supplied AnimatorBuilder and link the roads
	 * @param builder
	 */
	public void populateAnimatorBuilder(AnimatorBuilder builder) {
		
	    // Add Lights
	    for (int i=0; i<this._gridDimensions.getRow(); i++) {
	      for (int j=0; j<this._gridDimensions.getColumn(); j++) {
	        builder.addIntersection(_intersections[i][j], i, j);
	      }
	    }
	    
	    // Add Horizontal Roads
	    boolean alternating = false;
	    for (int i=0; i<this._gridDimensions.getRow(); i++) {
	      for (int j=0; j<=this._gridDimensions.getColumn(); j++) {
	    	 
	    		  builder.addHorizontalRoad(_horizontalRoads[i][j], i, j, alternating);
	    	  
	      }
	      //if this is a brand new road being created, create a source too
      	  Source s = (Source)VehicleAcceptorFactory.newSource(_ts);
	      Sink sink = (Sink)VehicleAcceptorFactory.newSink(_ts);
	      
	      this.linkRoad(_horizontalRoads[i], _intersections[i], s, sink, (alternating? TrafficGridPattern.Alternating:TrafficGridPattern.Simple));
	      
	      if (MP.simulationTrafficPattern == TrafficGridPattern.Alternating) alternating = !alternating; 
	    }	    
	    
	    
	    Intersection[][] vertIntersections = new Intersection[this._gridDimensions.getColumn()][this._gridDimensions.getRow()];
	    
	    for (int i=0;i<this._gridDimensions.getColumn();i++) {
	    	for (int j = 0; j <this._gridDimensions.getRow();j++) {
	    		vertIntersections[i][j] = _intersections[j][i];
	    	}
	    }
	    
	    
	    // Add Vertical Roads
	    alternating = false;
	    for (int j=0; j<this._gridDimensions.getColumn(); j++) {
	      for (int i=0; i<=this._gridDimensions.getRow(); i++) {
	   
	    		  builder.addVerticalRoad(_verticalRoads[j][i], i, j, alternating);
	      }
	      //if this is a brand new road being created, create a source too
      	  Source s = (Source)VehicleAcceptorFactory.newSource(_ts);
	      Sink sink = (Sink)VehicleAcceptorFactory.newSink(_ts);
	      this.linkRoad(_verticalRoads[j], vertIntersections[j], s, sink, (alternating? TrafficGridPattern.Alternating:TrafficGridPattern.Simple));
	      if (MP.simulationTrafficPattern == TrafficGridPattern.Alternating) alternating = !alternating; 
	    }	
		
	}
	
	
	
}