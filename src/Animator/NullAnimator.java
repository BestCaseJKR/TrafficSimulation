package Animator;

import util.Animator;

/**
 * Null object for {@link Animator}.
 * Made Public to allow for use during integration testing in Main package
 */
public class NullAnimator implements Animator {
  public void update(java.util.Observable o, Object arg) { }
  public void dispose() { }
}