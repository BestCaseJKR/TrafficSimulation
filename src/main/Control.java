package main;

import model.Model;
import model.MP;
import model.Agent.TimeServer;
import model.Agent.TimeServerLinked;
import model.TrafficGrid.TrafficGridPattern;
import model.swing.SwingAnimatorBuilder;
import ui.UIError;
import ui.PopupUI;
import ui.UI;
import ui.UIFactory;
import ui.UIFormBuilder;
import ui.UIFormTest;
import ui.UIMenu;
import ui.UIMenuAction;
import ui.UIMenuBuilder;

public class Control {
	/**
	 * Interface to implement strategy pattern and associate the menu state
	 * with a UIForm to show.
	 * @author John
	 *
	 */
	private interface StateMenu {
		/**
		 * return the UIMenu which should be associated with the state
		 * @return
		 */
		UIMenu getMenu();
	}
	/**
	 * Enum which reflects the available states for the controller.
	 * Takes a StateMenu implementor in its constructor
	 * @author John
	 *
	 */
	private enum ControlState {
		START(new StateMenu() {
			public UIMenu getMenu() {
					
					UIMenuBuilder menuBuilder = new UIMenuBuilder();
					 //set the default
					 menuBuilder.add("Default", new UIMenuAction() {
							public void run() {
								_ui.processMenu(ControlState.START.getMenu());
							} });
					 //add the run simulation command
					 menuBuilder.add("Run Simulation", new UIMenuAction() {
						public void run() {
								SwingAnimatorBuilder build = new SwingAnimatorBuilder();
								TimeServer ts = new TimeServerLinked();
								
								Model m = new Model(build, ts);
								m.run(MP.simulationRunTime);
						        m.dispose();
						}
					 });
					 /**
					  * add change parameters command.
					  * Note the local setupParameterMenu function.
					  * TODO Consider local function. Should it really be there?
					  */
					 menuBuilder.add("Change Parameters", new UIMenuAction() {
							
						 	public void run() {
						 		//when run is called, show the new menu
						 		_state = ControlState.PARAMETERS;
							}
						 	
								
							
					 });
					 menuBuilder.add("Exit", new UIMenuAction() {
						public void run() {
							_state = ControlState.EXIT;
						} });
					return menuBuilder.toUIMenu("Main Menu");
			}
		}),
		EXIT(new StateMenu(){

			@Override
			public UIMenu getMenu() {
				UIMenuBuilder b = new UIMenuBuilder();
				
				b.add("Default", new UIMenuAction() { public void run() { }});
				b.add("Yes", new UIMenuAction() {
					public void run() {
						_state = ControlState.EXITED;
					}
				});
				b.add("No", new UIMenuAction() {
					public void run() {
						_state = ControlState.START;
					}
				});
				return b.toUIMenu("Are you sure you want to exit?");
			}
			
		}),
		EXITED(new StateMenu() {

			@Override
			public UIMenu getMenu() {
				// TODO Auto-generated method stub
				return null;
			}
			
		}),
		PARAMETERS(new StateMenu() {

			public UIMenu getMenu() {
					UIMenuBuilder paramsMenu = new UIMenuBuilder();
					
					paramsMenu.add("Default", new UIMenuAction() {
						public void run() {	_ui.processMenu(ControlState.PARAMETERS.getMenu()); }	
						});
					
					paramsMenu.add("Show Current Values", new UIMenuAction() {
						public void run() {
							
							StringBuilder sb = new StringBuilder();
							
							PopupUI pop = new PopupUI();
							
							sb.append("Simulation Time Step:" + MP.simulationTimeStep);
							sb.append("\n");
							sb.append("Simulation Runtime:" + MP.simulationRunTime);
							sb.append("\n");											
							sb.append("Grid Size:[rows=" + MP.grid.getRow() + ",columns=" + MP.grid.getColumn() + "]");
							sb.append("\n");
							sb.append("Traffic Pattern:" + MP.simulationTrafficPatter);
							sb.append("\n");
							sb.append("Car Entry Rate:" + MP.carEntryRate);
							sb.append("\n");
							sb.append("Road Length:" + MP.roadSegmentLength);
							sb.append("\n");
							sb.append("Intersection Length:" + MP.intersectionLength);
							sb.append("\n");
							sb.append("Car Length:" + MP.carLength);
							sb.append("\n");
							sb.append("Max Car Velocity:" + MP.maxVelocity);
							sb.append("\n");
							sb.append("Car Stop Distance:" + MP.carStopDistance);
							sb.append("\n");
							sb.append("Car Break Distance:" + MP.breakDistance);
							sb.append("\n");
							sb.append("Yellow Light Time:" + MP.trafficLightYellowTime);
							sb.append("\n");
							sb.append("Green Light Time:" + MP.trafficLightGreenTime);
							sb.append("\n");
							pop.displayMessage(sb.toString());
							
						}						
					});
					paramsMenu.add("Simulation Timestep", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Current Value: " + MP.simulationTimeStep, _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Simulation Time Step"));
							MP.simulationTimeStep = Double.parseDouble(res[0]);
							}	
						});
					paramsMenu.add("Simulation Runtime", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Current Value: " + MP.simulationRunTime, _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Simulation Runtime"));
							MP.simulationRunTime = Double.parseDouble(res[0]);
							}	
						});					
					paramsMenu.add("Grid Size", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Rows[Current Value=" + MP.grid.getRow() + "]: " , _intTest);
							
							
							f.add("Columns[Current Value=" + MP.grid.getColumn() + "]: " , _intTest);
							String[] res = _ui.processForm(f.toUIForm("Grid Size"));
							MP.grid.setRow(Integer.parseInt(res[0]));
							MP.grid.setColumn(Integer.parseInt(res[1]));
							}	
						});
					
					paramsMenu.add("Traffic Pattern", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Current Value=" + MP.simulationTrafficPatter + "\n1 = Simple, 2 = Alternating : " , _intTest);
							String[] res = _ui.processForm(f.toUIForm("Traffic Pattern"));
							int resInt = Integer.parseInt(res[0]);
								if (resInt == 1) {
									MP.simulationTrafficPatter = TrafficGridPattern.Simple;
								} else if (resInt == 2) {
									MP.simulationTrafficPatter = TrafficGridPattern.Alternating;
								} else {
									_ui.displayError("Please enter 1 or 2");
								}
							}	
						});

					paramsMenu.add("Car Entry Rate", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Min Value=" + MP.carEntryRate.getMin() + "\n: " , _doubleTest);
							f.add("Max Value=" + MP.carEntryRate.getMax() + "\n: " , _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Car Entry Rate"));
							MP.carEntryRate.setMin(Integer.parseInt(res[0]));
							MP.carEntryRate.setMax(Integer.parseInt(res[1]));
							}
						});
					paramsMenu.add("Road Length", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Min Value=" + MP.roadSegmentLength.getMin() + "\n: " , _doubleTest);
							f.add("Max Value=" + MP.roadSegmentLength.getMax() + "\n: " , _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Road Length"));
							MP.roadSegmentLength.setMin(Integer.parseInt(res[0]));
							MP.roadSegmentLength.setMax(Integer.parseInt(res[1]));
							}
						});					
					paramsMenu.add("Intersection Length", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Min Value=" + MP.intersectionLength.getMin() + "\n: " , _doubleTest);
							f.add("Max Value=" + MP.intersectionLength.getMax() + "\n: " , _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Intersection Length"));
							MP.intersectionLength.setMin(Integer.parseInt(res[0]));
							MP.intersectionLength.setMax(Integer.parseInt(res[1]));
							}
						});	
					paramsMenu.add("Car Length", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Min Value=" + MP.carLength.getMin() + "\n: " , _doubleTest);
							f.add("Max Value=" + MP.carLength.getMax() + "\n: " , _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Car Length"));
							MP.carLength.setMin(Integer.parseInt(res[0]));
							MP.carLength.setMax(Integer.parseInt(res[1]));
							}
						});
					paramsMenu.add("Max Car Velocity", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Min Value=" + MP.maxVelocity.getMin() + "\n: " , _doubleTest);
							f.add("Max Value=" + MP.maxVelocity.getMax() + "\n: " , _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Max Car Velocity"));
							MP.maxVelocity.setMin(Integer.parseInt(res[0]));
							MP.maxVelocity.setMax(Integer.parseInt(res[1]));
							}
						});
					paramsMenu.add("Car Stop Distance", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Min Value=" + MP.carStopDistance.getMin() + "\n: " , _doubleTest);
							f.add("Max Value=" + MP.carStopDistance.getMax() + "\n: " , _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Car Stop Distance:"));
							MP.carStopDistance.setMin(Integer.parseInt(res[0]));
							MP.carStopDistance.setMax(Integer.parseInt(res[1]));
							}
						});
					paramsMenu.add("Car Break Distance", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Min Value=" + MP.breakDistance.getMin() + "\n: " , _doubleTest);
							f.add("Max Value=" + MP.breakDistance.getMax() + "\n: " , _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Car Break Distance:"));
							MP.breakDistance.setMin(Integer.parseInt(res[0]));
							MP.breakDistance.setMax(Integer.parseInt(res[1]));
							}
						});
					paramsMenu.add("Yellow Light Time", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Min Value=" + MP.trafficLightYellowTime.getMin() + "\n: " , _doubleTest);
							f.add("Max Value=" + MP.trafficLightYellowTime.getMax() + "\n: " , _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Yellow Light Time:"));
							MP.trafficLightYellowTime.setMin(Integer.parseInt(res[0]));
							MP.trafficLightYellowTime.setMax(Integer.parseInt(res[1]));
							}
						});
					paramsMenu.add("Green Light Time", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Min Value=" + MP.trafficLightGreenTime.getMin() + "\n: " , _doubleTest);
							f.add("Max Value=" + MP.trafficLightGreenTime.getMax() + "\n: " , _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Green Light Time:"));
							MP.trafficLightGreenTime.setMin(Integer.parseInt(res[0]));
							MP.trafficLightGreenTime.setMax(Integer.parseInt(res[1]));
							}
						});
					paramsMenu.add("Reset Parameters to Default And Return to Main Menu", new UIMenuAction() {
						public void run() {	
							MP.setDefaults();
							_state = ControlState.START; 
							}
						});
					paramsMenu.add("Return to Main Menu", new UIMenuAction() {
						public void run() {	_state = ControlState.START; }	
						});
					return paramsMenu.toUIMenu("Change Parameters");
			}
			
		});
		
		private StateMenu _stateMenu;
		
		ControlState(StateMenu menu) {
			_stateMenu = menu;
		}
		
		UIMenu getMenu() {
			return _stateMenu.getMenu();
		}
		
	
		
	}

	
	  private static ControlState _state = ControlState.START;

	  private static UIFormTest _intTest= new UIFormTest() {
	        public boolean run(String input) {
		          try {
		            Integer.parseInt(input);
		            return true;
		          } catch (NumberFormatException e) {
		            return false;
		          }
		        }
		      };
		      
	  private static UIFormTest _doubleTest= new UIFormTest() {
	        public boolean run(String input) {
		          try {
		            Double.parseDouble(input);
		            return true;
		          } catch (NumberFormatException e) {
		            return false;
		          }
		        }
		      };
		      
		      
	  private static UIFormTest _stringTest = new UIFormTest() {
	        public boolean run(String input) {
		          return ! "".equals(input.trim());
		        }
		      };
	    
	  
		      
	  private static UI _ui = UIFactory.ui();
	  
	  void run() {
	    try {
	      while (_state != ControlState.EXITED) {
	        _ui.processMenu(_state.getMenu());
	      }
	    } catch (ui.UIError e) {
	      _ui.displayError("UI closed");
	    }
	}
	  
} 
