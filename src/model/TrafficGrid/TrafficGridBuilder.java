package model.TrafficGrid;

import model.AnimatorBuilder;
import model.MP;
import model.TrafficPattern;
import model.Agent.Source;
import model.Agent.TimeServer;
import model.Vehicle.VehicleOrientation;
import model.VehicleAcceptor.Intersection;
import model.VehicleAcceptor.Road;
import model.VehicleAcceptor.Sink;
import model.VehicleAcceptor.VehicleAcceptor;
import model.VehicleAcceptor.VehicleAcceptorFactory;

public class TrafficGridBuilder {
	
	private final TrafficPattern _pattern;
	
	private final GridDimension _gridDimensions;
	
	private final TimeServer _ts;
	
	private Intersection[][] _intersections;
	
	private VehicleAcceptor[][] _horizontalRoads; // rows -> columns
	
	private VehicleAcceptor[][] _verticalRoads; // columns -> rows
	
	public TrafficGridBuilder(TrafficPattern pattern, GridDimension grid, TimeServer ts) {
		
		_pattern = pattern;
		
		_gridDimensions = grid;
		
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
	        _verticalRoads[i][j] = l;
	      }
	    }
	}
	
	public TrafficGrid toTrafficGridBuilder() {
		TrafficGridObj grid = new TrafficGridObj();
		
		grid.setGridDimensions(_gridDimensions);
		
		return grid;
	}
	
	private class TrafficGridObj implements TrafficGrid {
		
		private GridDimension _dim;
		
		public void setGridDimensions(GridDimension dimensions) {
			_dim = dimensions;
			
		}

		public GridDimension getGridDimensions() {
			return _dim;
		}

		public void populateBuilder(AnimatorBuilder builder) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
