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
/**
 * Control class implementing the Controller role in an MVC pattern.
 * Handles/validates all user input and provides the menu options
 * which can cause it to update parameters or run a model. 
 *
 */
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
		/**
		 * The initial main menu. 
		 * Build and return the Main menu using this anonymous class
		 */
		START(new StateMenu() {
			/**
			 * return the main menu
			 */
			public UIMenu getMenu() {
					//create the menu builder
					UIMenuBuilder menuBuilder = new UIMenuBuilder();
					 //set the default
					 menuBuilder.add("Default", new UIMenuAction() {
							public void run() {
								_ui.processMenu(ControlState.START.getMenu());
							} });
					 //add the run simulation command
					 menuBuilder.add("Run Simulation", new UIMenuAction() {
						public void run() {
								//create the swing animator builder, time server and model objects.
								
								SwingAnimatorBuilder build = new SwingAnimatorBuilder();
								TimeServer ts = new TimeServerLinked();
								
								Model m = new Model(build, ts);
								//run the simulation
								m.run(MP.simulationRunTime);
						        //dispose it
								m.dispose();
						}
					 });
					 /**
					  * add change parameters command.
					  * Note the local setupParameterMenu function.
					  * TODO Consider local function. Should it really be there?
					  */
					 menuBuilder.add("Change Parameters", new UIMenuAction() {
							/*
							 * (non-Javadoc)
							 * @see ui.UIMenuAction#run()
							 */
						 	public void run() {
						 		//when run is called, show the new menu
						 		_state = ControlState.PARAMETERS;
							}
					});
					/**
					 * Add the Exit command. Change the state to exit and return
					 */
					 menuBuilder.add("Exit", new UIMenuAction() {
						public void run() {
							_state = ControlState.EXIT;
						} });
					 
					 //now return the menu we just built
					return menuBuilder.toUIMenu("Main Menu");
			}
		}),
		/**
		 * The Exit Command Menu
		 */
		EXIT(new StateMenu(){

			@Override
			public UIMenu getMenu() {
				UIMenuBuilder b = new UIMenuBuilder();
				//set a basic default. required by the class
				b.add("Default", new UIMenuAction() { public void run() { }});
				//confirm the exit and switch the state accordingly.
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
				//return the exit menu
				return b.toUIMenu("Are you sure you want to exit?");
			}
			
		}),
		/**
		 * The Exited menu returns Null b/c the state merely confirms that
		 * the program should be exited
		 */
		EXITED(new StateMenu() {
			public UIMenu getMenu() {
				return null;
			}
		}),
		/**
		 * Build and return the parameters menu.
		 * TODO LOTS of room for improvement here
		 */
		PARAMETERS(new StateMenu() {
			//build and return the menu
			public UIMenu getMenu() {
					UIMenuBuilder paramsMenu = new UIMenuBuilder();
					//add a default option
					paramsMenu.add("Default", new UIMenuAction() {
						public void run() {	_ui.processMenu(ControlState.PARAMETERS.getMenu()); }	
						});
					/**
					 * Show the current MP values
					 */
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
							sb.append("Traffic Pattern:" + MP.simulationTrafficPattern);
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
							sb.append("Car Stop Distance:" + MP.stopDistance);
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
					//Edit the simulations timestep
					paramsMenu.add("Simulation Timestep", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Current Value: " + MP.simulationTimeStep, _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Simulation Time Step"));
							MP.simulationTimeStep = Double.parseDouble(res[0]);
							}	
						});
					//edit the simulation runtime
					paramsMenu.add("Simulation Runtime", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Current Value: " + MP.simulationRunTime, _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Simulation Runtime"));
							MP.simulationRunTime = Double.parseDouble(res[0]);
							}	
						});	
					//edit the grid size object. Note how two prompts are returned in 1 array
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
					//edit traffic pattern
					paramsMenu.add("Traffic Pattern", new UIMenuAction() {
						public void run() {	
							UIFormBuilder f = new UIFormBuilder();
							f.add("Current Value=" + MP.simulationTrafficPattern + "\n1 = Simple, 2 = Alternating : " , _intTest);
							String[] res = _ui.processForm(f.toUIForm("Traffic Pattern"));
							int resInt = Integer.parseInt(res[0]);
								if (resInt == 1) {
									MP.simulationTrafficPattern = TrafficGridPattern.Simple;
								} else if (resInt == 2) {
									MP.simulationTrafficPattern = TrafficGridPattern.Alternating;
								} else {
									_ui.displayError("Please enter 1 or 2");
								}
							}	
						});
					//edit the entry rate
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
					//edit the road length range
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
							f.add("Min Value=" + MP.stopDistance.getMin() + "\n: " , _doubleTest);
							f.add("Max Value=" + MP.stopDistance.getMax() + "\n: " , _doubleTest);
							String[] res = _ui.processForm(f.toUIForm("Car Stop Distance:"));
							MP.stopDistance.setMin(Integer.parseInt(res[0]));
							MP.stopDistance.setMax(Integer.parseInt(res[1]));
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
					/**
					 * Run a basic command on the MP static class to reset the parameters to default.
					 * Also switches state to Start to show the main menu
					 */
					paramsMenu.add("Reset Parameters to Default And Return to Main Menu", new UIMenuAction() {
						public void run() {	
							MP.setDefaults();
							_state = ControlState.START; 
							}
						});
					//set the state to start and show the main menu
					paramsMenu.add("Return to Main Menu", new UIMenuAction() {
						public void run() {	_state = ControlState.START; }	
						});
					
					
					//return the change param menu
					return paramsMenu.toUIMenu("Change Parameters");
			}
			
		});
		/**
		 * The StateMenu state of the controller.
		 * This state determines the menu which is shown
		 */
		private StateMenu _stateMenu;
		/**
		 * Enum Contructor
		 * @param menu the menu for the state
		 */
		ControlState(StateMenu menu) {
			_stateMenu = menu;
		}
		/**
		 * return the menu for the given state. Ex: START.getMenu() return the main menu UIMenu object.
		 * @return
		 */
		UIMenu getMenu() {
			return _stateMenu.getMenu();
		}
		
	
		
	}

		/**
		 * The state of the controller. default to START
		 */
	  private static ControlState _state = ControlState.START;
	  /**
	   * Inner class used to test user input for parseability as an integer.
	   */
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
	  /**
	   * Inner class used to test user input for parseability as an double.
	   */		      
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
		      
  /**
   * Inner class(currently unused) used to test user input for presence of a non-empty string
   */    
	  private static UIFormTest _stringTest = new UIFormTest() {
	        public boolean run(String input) {
		          return ! "".equals(input.trim());
		        }
		      };
	    
	  
	  /**
	   * the static UI factory class used to show forms and menus	      
	   */
	  private static UI _ui = UIFactory.ui();
	  /**
	   * Method to run/show the current ui menu objects.
	   * Loops until the ControlState == EXITED
	   */
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
