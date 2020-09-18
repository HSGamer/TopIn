package me.hsgamer.topin.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import me.hsgamer.hscore.bukkit.config.ConfigPath;
import me.hsgamer.hscore.bukkit.config.PluginConfig;
import me.hsgamer.hscore.bukkit.config.path.BooleanConfigPath;
import me.hsgamer.hscore.bukkit.config.path.IntegerConfigPath;
import me.hsgamer.hscore.bukkit.config.path.StringConfigPath;
import me.hsgamer.topin.config.path.StringListConfigPath;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainConfig extends PluginConfig {

  public static final BooleanConfigPath METRICS = new BooleanConfigPath("metrics", true);
  public static final IntegerConfigPath SAVE_PERIOD = new IntegerConfigPath("save.period", 600);
  public static final BooleanConfigPath SAVE_ASYNC = new BooleanConfigPath("save.async", false);
  public static final BooleanConfigPath SAVE_SILENT = new BooleanConfigPath("save.silent", false);
  public static final BooleanConfigPath UPDATE_ASYNC = new BooleanConfigPath("update.async", false);
  public static final IntegerConfigPath DISPLAY_TOP_START_INDEX = new IntegerConfigPath(
      "display-top-start-index", 1);
  public static final StringListConfigPath IGNORED_DATA_LIST = new StringListConfigPath(
      "ignored-data-list", Collections.emptyList());
  public static final StringConfigPath STORAGE_TYPE = new StringConfigPath(
      "storage-type", "JSON");
  public static final BooleanConfigPath ENABLE_PAPI_DATA_LIST = new BooleanConfigPath(
      "enable-papi-data-list", false);
  public static final ConfigPath<Map<String, String>> PAPI_DATA_LIST = new ConfigPath<>(
      "papi-data-list", Collections.emptyMap(),
      o -> {
        Map<String, String> map = new HashMap<>();
        if (o instanceof ConfigurationSection) {
          ((ConfigurationSection) o).getValues(false).forEach((k, v) -> {
            map.put(k, String.valueOf(v));
          });
        }
        return map;
      });

  public MainConfig(JavaPlugin plugin) {
    super(plugin, "config.yml");
    getConfig().options().copyDefaults(true);
    setDefaultPath();
    saveConfig();
  }

  private void setDefaultPath() {
    METRICS.setConfig(this);
    SAVE_PERIOD.setConfig(this);
    SAVE_ASYNC.setConfig(this);
    SAVE_SILENT.setConfig(this);
    UPDATE_ASYNC.setConfig(this);
    DISPLAY_TOP_START_INDEX.setConfig(this);
    IGNORED_DATA_LIST.setConfig(this);
    STORAGE_TYPE.setConfig(this);
    ENABLE_PAPI_DATA_LIST.setConfig(this);
    PAPI_DATA_LIST.setConfig(this);
  }
}
