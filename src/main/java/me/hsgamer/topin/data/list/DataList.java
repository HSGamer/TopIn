package me.hsgamer.topin.data.list;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import me.hsgamer.topin.config.PluginConfig;
import me.hsgamer.topin.data.value.PairDecimal;

public interface DataList {

  /**
   * Set the value of the unique id in the list
   *
   * @param uuid  the unique
   * @param value the value
   */
  void set(UUID uuid, BigDecimal value);

  /**
   * Add an unique id to the list
   *
   * @param uuid the unique id
   */
  default void add(UUID uuid) {
    set(uuid, null);
  }

  /**
   * Update all values of the list
   */
  void updateAll();

  /**
   * Get value from the pair
   *
   * @param uuid the unique id
   * @return the value
   */
  default Optional<BigDecimal> getValue(UUID uuid) {
    return getPair(uuid).map(PairDecimal::getValue);
  }

  /**
   * Get the pair of the unique id
   *
   * @param uuid the unique id
   * @return the pair
   */
  Optional<PairDecimal> getPair(UUID uuid);

  /**
   * Get the top list
   *
   * @param from start of the top
   * @param to   end of the top (exclusive)
   * @return the top list contains the pairs
   */
  List<PairDecimal> getTopRange(int from, int to);

  /**
   * Get the top list
   *
   * @param bound end of the top
   * @return the top list contains the pairs
   */
  default List<PairDecimal> getTop(int bound) {
    return getTopRange(0, bound);
  }

  /**
   * Get the pair from the list
   *
   * @param index the index
   * @return the pair
   */
  PairDecimal getPair(int index);

  /**
   * Load data from configuration file
   *
   * @param config the configuration file
   */
  void loadData(PluginConfig config);

  /**
   * Save data to configuration file
   *
   * @param config the configuration file
   */
  void saveData(PluginConfig config);

  /**
   * Get the display name of the data list
   *
   * @return the display name
   */
  String getDisplayName();
}
