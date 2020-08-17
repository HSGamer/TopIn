package me.hsgamer.topin.config;

import java.util.Collections;
import me.hsgamer.hscore.bukkit.config.PluginConfig;
import me.hsgamer.hscore.bukkit.config.path.BooleanConfigPath;
import me.hsgamer.hscore.bukkit.config.path.IntegerConfigPath;
import me.hsgamer.topin.config.path.StringListConfigPath;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainConfig extends PluginConfig {

  public static final IntegerConfigPath SAVE_PERIOD = new IntegerConfigPath("save.period", 600);
  public static final BooleanConfigPath SAVE_ASYNC = new BooleanConfigPath("save.async", false);
  public static final BooleanConfigPath SAVE_SILENT = new BooleanConfigPath("save.silent", false);
  public static final IntegerConfigPath DISPLAY_TOP_START_INDEX = new IntegerConfigPath(
      "display-top-start-index", 1);
  public static final StringListConfigPath IGNORED_DATA_LIST = new StringListConfigPath(
      "ignored-data-list", Collections.emptyList());

  public MainConfig(JavaPlugin plugin) {
    super(plugin, "config.yml");
    getConfig().options().copyDefaults(true);
    setDefaultPath();
    saveConfig();
  }

  private void setDefaultPath() {
    SAVE_PERIOD.setConfig(this);
    SAVE_ASYNC.setConfig(this);
    SAVE_SILENT.setConfig(this);
    DISPLAY_TOP_START_INDEX.setConfig(this);
    IGNORED_DATA_LIST.setConfig(this);
  }
}
