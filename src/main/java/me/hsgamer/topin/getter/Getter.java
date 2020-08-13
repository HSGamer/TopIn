package me.hsgamer.topin.getter;

/**
 * Receive and display information from the data list
 */
public interface Getter {

  /**
   * Check if this getter can be registered
   *
   * @return whether this getter can be registered
   */
  default boolean canRegister() {
    return true;
  }

  /**
   * Called when registering
   */
  void register();

  /**
   * Called when unregistering
   */
  void unregister();

  /**
   * Get the name of the getter
   *
   * @return the name
   */
  String getName();
}
