package me.hsgamer.topin.utils;

import java.util.Collection;

public class Validate {

  private Validate() {
    // EMPTY
  }

  /**
   * Check if it's null or empty
   *
   * @param list the list
   * @return whether it's null or empty
   */
  public static boolean isNullOrEmpty(Collection<?> list) {
    return list == null || list.isEmpty();
  }

  /**
   * Check if it's null or empty
   *
   * @param string the string
   * @return whether it's null or empty
   */
  public static boolean isNullOrEmpty(String string) {
    return string == null || string.isEmpty();
  }
}
