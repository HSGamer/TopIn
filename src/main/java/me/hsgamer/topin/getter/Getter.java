package me.hsgamer.topin.getter;

/**
 * Receive and display information from the data list
 */
public abstract class Getter {

  private State currentState = State.LOADED;

  /**
   * Handle the registration
   *
   * @return whether it's successful
   */
  public final boolean handleRegister() {
    if (currentState == State.ENABLED) {
      throw new IllegalStateException("This getter is already enabled");
    }
    if (register()) {
      currentState = State.ENABLED;
      return true;
    } else {
      currentState = State.DISABLED;
      return false;
    }
  }

  /**
   * Called when registering
   *
   * @return whether it's successful
   */
  public abstract boolean register();

  /**
   * Handle the unregistration
   */
  public final void handleUnregister() {
    if (currentState == State.DISABLED) {
      throw new IllegalStateException("This getter is already disabled");
    }
    unregister();
    currentState = State.DISABLED;
  }

  /**
   * Called when unregistering
   */
  public abstract void unregister();

  /**
   * Get the current state
   *
   * @return the current state
   */
  public final State getCurrentState() {
    return currentState;
  }

  /**
   * Get the name of the getter
   *
   * @return the name
   */
  public abstract String getName();

  /**
   * This contains the state of the getter
   */
  public enum State {
    /**
     * When the getter is initialized
     */
    LOADED,
    /**
     * When {@link #handleRegister()} is called
     */
    ENABLED,
    /**
     * When {@link #handleUnregister()} is called
     */
    DISABLED
  }
}
