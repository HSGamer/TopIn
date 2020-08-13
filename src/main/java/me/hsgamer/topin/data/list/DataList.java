package me.hsgamer.topin.data.list;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import me.hsgamer.hscore.bukkit.config.PluginConfig;
import me.hsgamer.hscore.bukkit.config.path.StringConfigPath;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MainConfig;
import me.hsgamer.topin.data.value.PairDecimal;

/**
 * The abstract class for all data lists
 */
public abstract class DataList {

  private final StringConfigPath displayName = new StringConfigPath("display-name." + getName(),
      getDefaultDisplayName());
  private final StringConfigPath suffix = new StringConfigPath("suffix." + getName(),
      getDefaultSuffix());

  public DataList() {
    MainConfig mainConfig = TopIn.getInstance().getMainConfig();
    displayName.setConfig(mainConfig);
    suffix.setConfig(mainConfig);
    mainConfig.saveConfig();
  }

  /**
   * Set the value of the unique id in the list
   *
   * @param uuid  the unique
   * @param value the value, nullable
   */
  public abstract void set(UUID uuid, BigDecimal value);

  /**
   * Add an unique id to the list
   *
   * @param uuid the unique id
   */
  public void add(UUID uuid) {
    set(uuid, null);
  }

  /**
   * Create a pair
   *
   * @param uuid the unique id
   * @return the pair
   */
  public abstract PairDecimal createPairDecimal(UUID uuid);

  /**
   * Update all values of the list
   */
  public abstract void updateAll();

  /**
   * Get value from the pair
   *
   * @param uuid the unique id
   * @return the value
   */
  public Optional<BigDecimal> getValue(UUID uuid) {
    return getPair(uuid).map(PairDecimal::getValue);
  }

  /**
   * Get the pair of the unique id
   *
   * @param uuid the unique id
   * @return the pair
   */
  public abstract Optional<PairDecimal> getPair(UUID uuid);

  /**
   * Get the top list
   *
   * @param from start of the top
   * @param to   end of the top (exclusive)
   * @return the top list contains the pairs
   */
  public abstract List<PairDecimal> getTopRange(int from, int to);

  /**
   * Get the top list
   *
   * @param bound end of the top
   * @return the top list contains the pairs
   */
  public List<PairDecimal> getTop(int bound) {
    return getTopRange(0, bound);
  }

  /**
   * Get the pair from the list
   *
   * @param index the index
   * @return the pair
   */
  public abstract PairDecimal getPair(int index);

  /**
   * Get the index of the unique id
   *
   * @param uuid the unique id
   * @return the index
   */
  public abstract Optional<Integer> getTopIndex(UUID uuid);

  /**
   * Load data from configuration file
   *
   * @param config the configuration file
   */
  public abstract void loadData(PluginConfig config);

  /**
   * Save data to configuration file
   *
   * @param config the configuration file
   */
  public abstract void saveData(PluginConfig config);

  /**
   * Get the technical name of the data list
   *
   * @return the display name
   */
  public abstract String getName();

  /**
   * Get the size of the data list
   *
   * @return the size
   */
  public abstract int getSize();

  /**
   * Get the default display name of the data list
   *
   * @return the display name
   */
  public abstract String getDefaultDisplayName();

  /**
   * Get the default suffix of the value
   *
   * @return the suffix
   */
  public abstract String getDefaultSuffix();

  /**
   * Get the display name of the data list
   *
   * @return the display name
   */
  public String getDisplayName() {
    return displayName.getValue();
  }

  /**
   * Get the suffix of the value
   *
   * @return the suffix
   */
  public String getSuffix() {
    return suffix.getValue();
  }
}
