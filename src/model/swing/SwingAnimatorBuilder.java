package model.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import Animator.AnimatorBuilder;
import model.MP;
import model.Vehicle.Vehicle;
import model.VehicleAcceptor.Intersection;
import model.VehicleAcceptor.Light;
import model.VehicleAcceptor.LightState;
import model.VehicleAcceptor.VehicleAcceptor;
import util.Animator;
import util.SwingAnimator;
import util.SwingAnimatorPainter;

/** 
 * A class for building Animators.
 */
public class SwingAnimatorBuilder implements AnimatorBuilder {
  MyPainter _painter;
  public SwingAnimatorBuilder() {
    _painter = new MyPainter();
  }
  public Animator getAnimator() {
    if (_painter == null) { throw new IllegalStateException(); }
    Animator returnValue = new SwingAnimator(_painter, "Traffic Simulator",
                                             VP.displayWidth, VP.displayHeight, VP.displayDelay);
    _painter = null;
    return returnValue;
  }
  private static double skipInit = VP.gap;
  private static double skipRoad = VP.gap + MP.baseRoadLength;
  private static double skipCar = VP.gap + VP.elementWidth;
  private static double skipRoadCar = skipRoad + skipCar;
  public void addLight(Light d, int i, int j) {
    double x = skipInit + skipRoad + j*skipRoadCar;
    double y = skipInit + skipRoad + i*skipRoadCar;
    Translator t = new TranslatorWE(x, y, MP.baseCarLength, VP.elementWidth, VP.scaleFactor);
    _painter.addLight(d,t);
  }
  public void addHorizontalRoad(VehicleAcceptor l, int i, int j, boolean eastToWest) {
    double x = skipInit + j*skipRoadCar;
    double y = skipInit + skipRoad + i*skipRoadCar;
    Translator t = eastToWest ? new TranslatorEW(x, y, MP.baseRoadLength, VP.elementWidth, VP.scaleFactor)
                              : new TranslatorWE(x, y, MP.baseRoadLength, VP.elementWidth, VP.scaleFactor);
    _painter.addRoad(l,t);
  }
  public void addVerticalRoad(VehicleAcceptor l, int i, int j, boolean southToNorth) {
    double x = skipInit + skipRoad + j*skipRoadCar;
    double y = skipInit + i*skipRoadCar;
    Translator t = southToNorth ? new TranslatorSN(x, y, MP.baseRoadLength, VP.elementWidth, VP.scaleFactor)
                                : new TranslatorNS(x, y, MP.baseRoadLength, VP.elementWidth, VP.scaleFactor);
    _painter.addRoad(l,t);
  }

	public void addIntersection(Intersection intersection, int i, int j) {
	    double x = skipInit + skipRoad + j*skipRoadCar;
	    double y = skipInit + skipRoad + i*skipRoadCar;
	    Translator t = new TranslatorWE(x, y, MP.baseIntersectionLength, VP.elementWidth, VP.scaleFactor);
	    _painter.addIntersection(intersection,t);
	}

  /** Class for drawing the Model. */
  private static class MyPainter implements SwingAnimatorPainter {

    /** Pair of a model element <code>x</code> and a translator <code>t</code>. */
    private static class Element<T> {
      T x;
      Translator t;
      Element(T x, Translator t) {
        this.x = x;
        this.t = t;
      }
    }
    
    private List<Element<VehicleAcceptor>> _roadElements;
    private List<Element<Intersection>> _intersectionElements;
    private List<Element<Light>> _lightElements;
    MyPainter() {
      _roadElements = new ArrayList<Element<VehicleAcceptor>>();
      _intersectionElements = new ArrayList<Element<Intersection>>();
      _lightElements = new ArrayList<Element<Light>>();
    }    
    void addLight(Light x, Translator t) {
      _lightElements.add(new Element<Light>(x,t));
    }
    void addRoad(VehicleAcceptor x, Translator t) {
      _roadElements.add(new Element<VehicleAcceptor>(x,t));
    }
    void addIntersection(Intersection x, Translator t) {
    	_intersectionElements.add(new Element<Intersection>(x,t));
    }
    public void paint(Graphics g) {
      // This method is called by the swing thread, so may be called
      // at any time during execution...

      // First draw the background elements
      /*
      for (Element<Light> e : _lightElements) {
        if (e.x.getState() == LightState.GreenNS_RedEW) {
          g.setColor(Color.GREEN);
        } else if(e.x.getState() == LightState.YellowNS_RedEW) {
          g.setColor(Color.YELLOW);
        } else {
          g.setColor(Color.RED);
        }
        XGraphics.fillOval(g, e.t, 0, 0, MP.baseCarLength, VP.elementWidth);
      } */
      
    	for (Element<Intersection> e : _intersectionElements) {
            if (e.x.getLightControl().getState() == LightState.GreenNS_RedEW) {
              g.setColor(Color.GREEN);
            } else if(e.x.getLightControl().getState() == LightState.YellowNS_RedEW) {
              g.setColor(Color.YELLOW);
            } else {
              g.setColor(Color.RED);
            }
            
            for (Vehicle d : e.x.getCars().toArray(new Vehicle[0])) {
                g.setColor(d.getColor());
                XGraphics.fillOval(g, e.t, normalizeVehicleValueIntersection(d.getPosition(), e.x), 0, normalizeVehicleValueIntersection(d.getLength(), e.x), VP.elementWidth);
             }
            XGraphics.fillOval(g, e.t, 0, 0, MP.baseCarLength, VP.elementWidth);
    	}	
    	
      g.setColor(Color.BLACK);
      for (Element<VehicleAcceptor> e : _roadElements) {
        XGraphics.fillRect(g, e.t, 0, 0, MP.baseRoadLength, VP.elementWidth);
      }
      
      // Then draw the foreground elements
      for (Element<VehicleAcceptor> e : _roadElements) {
        // iterate through a copy because e.x.getCars() may change during iteration...
        for (Vehicle d : e.x.getCars().toArray(new Vehicle[0])) {
          g.setColor(d.getColor());
          XGraphics.fillOval(g, e.t, normalizeVehicleValue((d.getPosition() - d.getLength() ) , e.x), 0, normalizeVehicleValue(d.getLength(), e.x), VP.elementWidth);
        }
      }
    }
    /**
     * normalize a 
     * @param v
     * @return
     */
    private double normalizeVehicleValue(double val, VehicleAcceptor va) {
    	return (val/va.getLength()) * MP.baseRoadLength;
    }
    
    private double normalizeVehicleValueIntersection(double val, VehicleAcceptor va) {
    	return (val/va.getLength()) * MP.baseIntersectionLength;
    }
  }

}

