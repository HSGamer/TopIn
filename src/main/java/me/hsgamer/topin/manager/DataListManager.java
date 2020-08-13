package me.hsgamer.topin.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import me.hsgamer.hscore.bukkit.config.PluginConfig;
import me.hsgamer.topin.data.list.DataList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The data list manager
 */
public final class DataListManager {

  private final Map<String, DataList> dataListMap = new HashMap<>();
  private final Map<String, PluginConfig> dataConfigMap = new HashMap<>();
  private final JavaPlugin plugin;
  private final File dataDir;

  public DataListManager(JavaPlugin plugin) {
    this.plugin = plugin;
    this.dataDir = new File(plugin.getDataFolder(), "data");
    if (!dataDir.exists()) {
      dataDir.mkdirs();
    }
  }

  /**
   * Register a data list
   *
   * @param dataList the data list
   */
  public void register(DataList dataList) {
    dataListMap.computeIfAbsent(dataList.getName(), s -> {
      PluginConfig config = new PluginConfig(plugin, new File(dataDir, s + ".yml"));
      dataConfigMap.put(s, config);
      dataList.loadData(config);
      return dataList;
    });
  }

  /**
   * Save all data lists to file
   */
  public void saveAll() {
    dataListMap.forEach((name, dataList) -> dataList.saveData(dataConfigMap.get(name)));
  }

  /**
   * Get the data list
   *
   * @param name the name
   * @return the data list
   */
  public Optional<DataList> getDataList(String name) {
    return Optional.ofNullable(dataListMap.get(name));
  }

  /**
   * Get the list of the available data lists' name
   *
   * @return the list of name
   */
  public Collection<String> getDataListNames() {
    return dataListMap.keySet();
  }

  /**
   * Clear all data lists
   */
  public void clearAll() {
    dataListMap.clear();
    dataConfigMap.clear();
  }

  /**
   * Add a new unique id to the data list
   *
   * @param uuid the unique id
   */
  public void addNew(UUID uuid) {
    dataListMap.values().forEach(dataList -> dataList.add(uuid));
  }

  /**
   * Get the suggested data list
   *
   * @param start the start of the names
   * @return the suggested names
   */
  public List<String> getSuggestedDataListNames(String start) {
    List<String> list = new ArrayList<>(getDataListNames());
    if (start != null && !start.isEmpty()) {
      list.removeIf(s -> !s.startsWith(start));
    }
    return list;
  }
}
