package me.hsgamer.topin.manager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import me.hsgamer.topin.config.PluginConfig;
import me.hsgamer.topin.data.DataList;
import org.bukkit.plugin.java.JavaPlugin;

public class DataListManager {

  private final Map<String, DataList> dataListMap = new HashMap<>();
  private final Map<String, PluginConfig> dataConfigMap = new HashMap<>();
  private final JavaPlugin plugin;
  private final File dataDir;

  public DataListManager(JavaPlugin plugin) {
    this.plugin = plugin;
    this.dataDir = new File(plugin.getDataFolder(), "data");
  }

  /**
   * Register a data list
   *
   * @param name     the name of the data list
   * @param dataList the data list
   */
  public void register(String name, DataList dataList) {
    dataListMap.computeIfAbsent(name, s -> {
      PluginConfig config = new PluginConfig(plugin, new File(dataDir, name + ".yml"));
      dataConfigMap.put(name, config);
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
}
