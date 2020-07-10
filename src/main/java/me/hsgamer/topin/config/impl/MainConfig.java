package me.hsgamer.topin.config.impl;

import me.hsgamer.topin.config.ConfigPath;
import me.hsgamer.topin.config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class MainConfig extends PluginConfig {

  public static final ConfigPath<Integer> UPDATE_PERIOD = new ConfigPath<>(Integer.class,
      "update.period", 2000);
  public static final ConfigPath<Boolean> UPDATE_ASYNC = new ConfigPath<>(Boolean.class,
      "update.async", false);

  public MainConfig(JavaPlugin plugin) {
    super(plugin, "config.yml");
    getConfig().options().copyDefaults(true);
    setDefaultPath();
    saveConfig();
  }

  private void setDefaultPath() {
    UPDATE_PERIOD.setConfig(this);
    UPDATE_ASYNC.setConfig(this);
  }
}
