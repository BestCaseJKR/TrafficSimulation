package model.TrafficGrid;

import model.AnimatorBuilder;
import model.MP;
import model.Agent.TimeServer;
import model.Vehicle.VehicleOrientation;
import model.VehicleAcceptor.Intersection;
import model.VehicleAcceptor.Sink;
import model.VehicleAcceptor.Source;
import model.VehicleAcceptor.VehicleAcceptor;
import model.VehicleAcceptor.VehicleAcceptorFactory;

public class TrafficGridBuilder {
	
	private final GridDimension _gridDimensions;
	
	private final TimeServer _ts;
	
	private Intersection[][] _intersections;
	
	private VehicleAcceptor[][] _horizontalRoads; // rows -> columns
	
	private VehicleAcceptor[][] _verticalRoads; // columns -> rows
	
	public TrafficGridBuilder(int rows, int columns, TimeServer ts) {
		
		_gridDimensions = new GridDimension(rows, columns) {
			
		};
		
		_ts = ts;
		
	}
	
	
	public void buildIntersections() {
		
		
	    _intersections = new Intersection[_gridDimensions.getRow()][_gridDimensions.getColumn()];
	    // Add Lights
	    for (int i=0; i<_gridDimensions.getRow(); i++) {
	      for (int j=0; j<_gridDimensions.getColumn(); j++) {
	    	  
	    	  _intersections[i][j] = (Intersection)VehicleAcceptorFactory.newIntersection(_ts);
	      }
	    }
		
	}
	
	public void buildHorizontalRoads() {
		// Add Horizontal Roads
		_horizontalRoads = new VehicleAcceptor[_gridDimensions.getRow()][_gridDimensions.getColumn()+1];
		 
		for (int i=0; i<_gridDimensions.getRow(); i++) {
	      for (int j=0; j<=_gridDimensions.getColumn(); j++) {
	        VehicleAcceptor l = VehicleAcceptorFactory.newRoad();
	        //l.setOrientation(((eastToWest)? RoadOrientation.East_West: RoadOrientation.West_East ));
	        l.setOrientation(VehicleOrientation.East_West);
	        _horizontalRoads[i][j] = l;
	      }
	    }
	}
	
	public void buildVerticalRoads() {
		_verticalRoads = new VehicleAcceptor[_gridDimensions.getColumn()][_gridDimensions.getRow()+1];
		
	    for (int j=0; j<_gridDimensions.getColumn(); j++) {
	      for (int i=0; i<=_gridDimensions.getRow(); i++) {
	    	  VehicleAcceptor l = VehicleAcceptorFactory.newRoad();
	        l.setOrientation(VehicleOrientation.North_South);
	        _verticalRoads[j][i] = l;
	      }
	    }
	    
	}	
	
	private void linkRoad(VehicleAcceptor[] roads,
				Intersection[] intersections, VehicleAcceptor source,
				VehicleAcceptor sink, TrafficGridPattern alternating) {
		//pass the logic to the state
		alternating.linkRoad(roads, intersections, source, sink);
	}
	
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
	      
	      if (MP.simulationTrafficPatter == TrafficGridPattern.Alternating) alternating = !alternating; 
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
	      if (MP.simulationTrafficPatter == TrafficGridPattern.Alternating) alternating = !alternating; 
	    }	
		
	}
	
	
	
}